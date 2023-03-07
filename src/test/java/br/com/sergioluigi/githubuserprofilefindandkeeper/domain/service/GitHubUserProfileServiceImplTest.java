package br.com.sergioluigi.githubuserprofilefindandkeeper.domain.service;

import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.GithubUserProfile;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileFindAndKeeperException;
import br.com.sergioluigi.githubuserprofilefindandkeeper.fixture.GithubUserProfileFixture;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.http.github.GitHubFindUserProfileClientImpl;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.repository.GitHubSaveUserProfileRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

import static br.com.sergioluigi.githubuserprofilefindandkeeper.TestUtils.assertError;
import static br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError.USERNAME_REQUIRED;
import static br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError.USER_PROFILE_NOT_FOUND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitHubUserProfileServiceImplTest {

    @Mock
    private GitHubFindUserProfileClientImpl githubFindUserProfileClient;

    @Mock
    private GitHubSaveUserProfileRepositoryImpl gitHubSaveUserProfileRepository;

    @Mock
    private GitHubUserProfileUpdateWhenChangedServiceImpl gitHubUserProfileUpdateWhenChangedService;

    @InjectMocks
    private GitHubUserProfileServiceImpl gitHubUserProfileService;

    private GithubUserProfile dummyProfile;

    @BeforeEach
    public void beforeEach(){
        dummyProfile = GithubUserProfileFixture.getOneDummyProfile();
    }

    @Test
    void shouldReturnAGithubUserProfile() {

        var profileMono = Mono.just(dummyProfile);

        when(githubFindUserProfileClient.execute(any())).thenReturn(profileMono);

        when(gitHubUserProfileUpdateWhenChangedService.execute(any())).thenReturn(Mono.empty());

        when(gitHubSaveUserProfileRepository.execute(any())).thenReturn(profileMono);

        StepVerifier.create(gitHubUserProfileService.findByUsername(dummyProfile.getLogin()))
                .expectNext(dummyProfile)
                .verifyComplete();

    }

    @Test
    void shouldReturnAnUpdatedGithubUserProfile() {

        var profileMono = Mono.just(dummyProfile);

        when(githubFindUserProfileClient.execute(any())).thenReturn(profileMono);

        when(gitHubSaveUserProfileRepository.execute(any())).thenReturn(profileMono);

        when(gitHubUserProfileUpdateWhenChangedService.execute(any())).thenReturn(profileMono);

        StepVerifier.create(gitHubUserProfileService.findByUsername(dummyProfile.getLogin()))
                .expectNext(dummyProfile)
                .verifyComplete();

    }

    @Test
    void shouldThrowExceptionUserNameRequired() {

        StepVerifier.create(gitHubUserProfileService.findByUsername(null))
                .verifyErrorMatches(assertError(USERNAME_REQUIRED));

    }

    @Test
    void shouldThrowExceptionUserProfileNotFound() {

        when(githubFindUserProfileClient.execute(any())).thenReturn(Mono.error(new GitHubUserProfileFindAndKeeperException(USER_PROFILE_NOT_FOUND)));

        StepVerifier.create(gitHubUserProfileService.findByUsername("xxx"))
                .verifyErrorMatches(assertError(USER_PROFILE_NOT_FOUND));

    }
}