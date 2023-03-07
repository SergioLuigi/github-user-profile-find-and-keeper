package br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.repository;

import br.com.sergioluigi.githubuserprofilefindandkeeper.TestUtils;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.GithubUserProfile;
import br.com.sergioluigi.githubuserprofilefindandkeeper.fixture.GithubUserProfileFixture;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.entity.GithubUserProfileEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static br.com.sergioluigi.githubuserprofilefindandkeeper.TestUtils.assertError;
import static br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError.USER_PROFILE_REQUIRED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitHubSaveUserProfileRepositoryImplTest {

    @Mock
    private GitHubUserProfileRepository gitHubUserProfileRepository;

    @InjectMocks
    private GitHubSaveUserProfileRepositoryImpl gitHubSaveUserProfileRepository;

    private GithubUserProfile dummyProfile;

    private GithubUserProfileEntity dummyProfileEntity;

    @BeforeEach
    public void beforeEach(){
        dummyProfileEntity = GithubUserProfileFixture.getOneGithubUserProfileEntity();
        dummyProfile = GithubUserProfileFixture.getOneDummyProfile();
    }

    @Test
    void shouldReturnGitHubUserProfile() {

        when(gitHubUserProfileRepository.save(any())).thenReturn(Mono.just(dummyProfileEntity));

        StepVerifier.create(gitHubSaveUserProfileRepository.execute(dummyProfile))
                .expectNext(dummyProfile)
                .verifyComplete();


    }

    @Test
    void shouldThrowUserProfileRequired() {

        StepVerifier.create(gitHubSaveUserProfileRepository.execute(null))
                .expectErrorMatches(assertError(USER_PROFILE_REQUIRED))
                .verify();

    }
}