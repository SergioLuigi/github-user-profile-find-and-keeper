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
class GitHubFindUserProfileByGitHubIdRepositoryImplTest {

    @Mock
    private GitHubUserProfileRepository gitHubUserProfileRepository;

    @InjectMocks
    private GitHubFindUserProfileByGitHubIdRepositoryImpl gitHubFindUserProfileByGitHubIdRepository;

    private GithubUserProfile dummyProfile;

    private GithubUserProfileEntity dummyProfileEntity;

    @BeforeEach
    public void beforeEach(){
        dummyProfileEntity = GithubUserProfileFixture.getOneGithubUserProfileEntity();
        dummyProfile = GithubUserProfileFixture.getOneDummyProfile();
    }

    @Test
    void shouldReturnGithubUserProfile() {

        when(gitHubUserProfileRepository.findByGithubId(any())).thenReturn(Mono.just(dummyProfileEntity));

        StepVerifier.create(gitHubFindUserProfileByGitHubIdRepository.execute(dummyProfile))
                .expectNext(dummyProfile)
                .verifyComplete();


    }

    @Test
    void shouldThrowUserProfileRequired() {

        StepVerifier.create(gitHubFindUserProfileByGitHubIdRepository.execute(null))
                .expectErrorMatches(assertError(USER_PROFILE_REQUIRED))
                .verify();


    }
}