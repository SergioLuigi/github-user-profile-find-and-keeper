package br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.http.github;

import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.GithubUserProfile;
import br.com.sergioluigi.githubuserprofilefindandkeeper.fixture.GithubUserProfileFixture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.Random;

import static br.com.sergioluigi.githubuserprofilefindandkeeper.TestUtils.assertError;
import static br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError.USER_PROFILE_INTERNAL_SERVER_ERROR;
import static br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError.USER_PROFILE_NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class GitHubFindUserProfileClientImplTest {

    public static MockWebServer mockBackEnd;

    private GitHubFindUserProfileClientImpl gitHubFindUserProfileClient;

    private GithubUserProfile dummyProfile;

    private ObjectMapper objectMapper;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    public void beforeEach(){
        dummyProfile = GithubUserProfileFixture.getOneDummyProfile();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        var webClient = WebClient.create("http://localhost:"+mockBackEnd.getPort());
        gitHubFindUserProfileClient = new GitHubFindUserProfileClientImpl(webClient);
    }

    @Test
    public void shouldReturnGithubUserProfile() throws JsonProcessingException {

        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(dummyProfile))
                .addHeader("Content-Type", "application/json"));

        StepVerifier.create(gitHubFindUserProfileClient.execute("xxxx"))
                .expectNext(dummyProfile)
                .verifyComplete();

    }

    @Test
    public void shouldThrowUserProfileNotFound() throws JsonProcessingException {

        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(dummyProfile))
                .setResponseCode(404)
                .addHeader("Content-Type", "application/json"));

        StepVerifier.create(gitHubFindUserProfileClient.execute("xxxx"))
                .expectErrorMatches(assertError(USER_PROFILE_NOT_FOUND))
                .verify();

    }
    @Test
    public void shouldThrowUserProfileInternalServerError() throws JsonProcessingException {

        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(dummyProfile))
                .setResponseCode(new Random().nextInt(600 - 405) + 405)
                .addHeader("Content-Type", "application/json"));

        StepVerifier.create(gitHubFindUserProfileClient.execute("xxxx"))
                .expectErrorMatches(assertError(USER_PROFILE_INTERNAL_SERVER_ERROR))
                .verify();

    }
}