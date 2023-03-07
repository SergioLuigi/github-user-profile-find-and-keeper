package br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.http.api.controller;

import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.GithubUserProfile;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.service.GitHubUserProfileServiceImpl;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.service.GitHubUserProfileUpdateWhenChangedServiceImpl;
import br.com.sergioluigi.githubuserprofilefindandkeeper.fixture.GithubUserProfileFixture;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.exception.ErrorDTO;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.http.dto.GithubUserProfileResponse;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.GitHubUserProfileDatabaseIntegrationConfig;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.entity.GithubUserProfileEntity;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.repository.GitHubFindUserProfileByGitHubIdRepositoryImpl;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.repository.GitHubSaveUserProfileRepositoryImpl;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.repository.GitHubUserProfileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.MediaType;
import org.mockserver.springtest.MockServerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

import static br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError.USER_PROFILE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;


@Testcontainers
@ActiveProfiles("integration")
@AutoConfigureWebTestClient(timeout = "30000")
@MockServerTest({"${github.api}=http://localhost:${mockServerPort}"})
@Import({
        GithubUserController.class,
        GitHubUserProfileServiceImpl.class,
        GitHubSaveUserProfileRepositoryImpl.class,
        GitHubUserProfileDatabaseIntegrationConfig.class,
        GitHubFindUserProfileByGitHubIdRepositoryImpl.class,
        GitHubUserProfileUpdateWhenChangedServiceImpl.class
})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubUserControllerTest {


    private MockServerClient mockServerClient;

    @Autowired
    private GitHubUserProfileRepository gitHubProfileRepository;

    @Autowired
    private WebTestClient webTestClient;

    private GithubUserProfile dummyProfile;

    private GithubUserProfile dummyUpdatedProfile;

    private GithubUserProfileEntity dummyProfileEntity;

    private GithubUserProfileResponse dummyProfileResponse;

    private GithubUserProfileResponse dummyUpdatedProfileResponse;

    private ObjectMapper objectMapper;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.2");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://"
                + postgreSQLContainer.getHost() + ":" + postgreSQLContainer.getFirstMappedPort()
                + "/" + postgreSQLContainer.getDatabaseName());
        registry.add("spring.r2dbc.password", postgreSQLContainer::getPassword);
        registry.add("spring.r2dbc.username", postgreSQLContainer::getUsername);
        registry.add("spring.liquibase.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.liquibase.password", postgreSQLContainer::getPassword);
        registry.add("spring.liquibase.user", postgreSQLContainer::getUsername);
    }

    @BeforeEach
    public void beforeEach() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        dummyProfile = GithubUserProfileFixture.getOneDummyProfile();
        dummyProfileResponse = GithubUserProfileFixture.getOneDummyProfileResponse();
        dummyProfileEntity = GithubUserProfileFixture.getOneGithubUserProfileEntity();
        dummyUpdatedProfile = GithubUserProfileFixture.getOneUpdatedDummyProfile();
        dummyUpdatedProfileResponse = new GithubUserProfileResponse(dummyUpdatedProfile);
    }

    @Test
    public void shouldReturnGitHubUserProfileResponse() throws JsonProcessingException {

        mockServerClient
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/"+dummyProfile.getLogin())
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody(objectMapper.writeValueAsString(dummyProfile))
                );

        webTestClient.get()
                .uri("/users/{username}",dummyProfile.getLogin())
                .exchange()
                .expectBody(GithubUserProfileResponse.class)
                .isEqualTo(dummyProfileResponse);
    }

    @Test
    public void shouldThrowUserProfileNotFound() {

        mockServerClient
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/"+dummyProfile.getLogin())
                )
                .respond(
                        response()
                                .withStatusCode(404)
                );


        webTestClient.get()
                .uri("/users/{username}","xxx")
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(ErrorDTO.class)
                .consumeWith(response ->{
                    assertEquals(USER_PROFILE_NOT_FOUND.getHttpStatus().value(), Objects.requireNonNull(response.getResponseBody()).getStatus());
                    assertEquals(USER_PROFILE_NOT_FOUND.getReason(), response.getResponseBody().getMessage());
                });
    }

    @Test
    public void shouldUpdateAndReturnUserProfile() throws JsonProcessingException {
        gitHubProfileRepository.deleteAll().block();
        dummyProfileEntity.setId(null);
        gitHubProfileRepository.save(dummyProfileEntity).block();

        mockServerClient
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/"+dummyUpdatedProfile.getLogin())
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody(objectMapper.writeValueAsString(dummyUpdatedProfile))
                );

        webTestClient.get()
                .uri("/users/{username}",dummyProfile.getLogin())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(GithubUserProfileResponse.class)
                .isEqualTo(dummyUpdatedProfileResponse);
    }

}