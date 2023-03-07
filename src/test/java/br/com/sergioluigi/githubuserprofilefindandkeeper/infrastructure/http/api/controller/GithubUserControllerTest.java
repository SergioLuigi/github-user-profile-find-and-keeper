package br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.http.api.controller;

import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.GithubUserProfile;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileFindAndKeeperException;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.service.GitHubUserProfileServiceImpl;
import br.com.sergioluigi.githubuserprofilefindandkeeper.fixture.GithubUserProfileFixture;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.http.dto.GithubUserProfileResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError.USERNAME_REQUIRED;
import static br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError.USER_PROFILE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@WebFluxTest(GithubUserController.class)
class GithubUserControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private GitHubUserProfileServiceImpl gitHubUserProfileService;

    private GithubUserProfile dummyProfile;

    private GithubUserProfileResponse dummyProfileResponse;

    @BeforeEach
    public void beforeEach(){
        dummyProfile = GithubUserProfileFixture.getOneDummyProfile();
        dummyProfileResponse = GithubUserProfileFixture.getOneDummyProfileResponse();
    }

    @Test
    public void shouldReturnGitHubUserProfileResponse(){

        when(gitHubUserProfileService.findByUsername(any())).thenReturn(Mono.justOrEmpty(dummyProfile));

        webClient.get().uri("/users/{username}","xxxxx")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(GithubUserProfileResponse.class)
                .consumeWith(response -> assertEquals(dummyProfileResponse, response.getResponseBody()) );
    }

    @Test
    public void shouldThrowUserProfileNotFound(){

        when(gitHubUserProfileService.findByUsername(any())).thenReturn(Mono.error(new GitHubUserProfileFindAndKeeperException(USER_PROFILE_NOT_FOUND)));

        webClient.get().uri("/users/{username}","xxxxx")
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody()
                .jsonPath("$.error").isEqualTo("Not Found")
                .jsonPath("$.message").isEqualTo("User profile not found");
    }



}