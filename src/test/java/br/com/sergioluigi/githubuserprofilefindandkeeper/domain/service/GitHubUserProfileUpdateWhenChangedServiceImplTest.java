package br.com.sergioluigi.githubuserprofilefindandkeeper.domain.service;

import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.GithubUserProfile;
import br.com.sergioluigi.githubuserprofilefindandkeeper.fixture.GithubUserProfileFixture;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.entity.GithubUserProfileEntity;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.repository.GitHubUserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static br.com.sergioluigi.githubuserprofilefindandkeeper.TestUtils.assertError;
import static br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError.USER_PROFILE_REQUIRED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitHubUserProfileUpdateWhenChangedServiceImplTest {

    @Mock
    private GitHubUserProfileRepository gitHubUserProfileRepository;

    @InjectMocks
    private GitHubUserProfileUpdateWhenChangedServiceImpl gitHubUserProfileUpdateWhenChangedService;

    private GithubUserProfile dummyProfile;

    private GithubUserProfileEntity dummyProfileEntity;

    private GithubUserProfileEntity dummyUpdatedProfileEntity;

    private GithubUserProfile updatedDummyProfile;

    @BeforeEach
    public void beforeEach(){
        dummyProfile = GithubUserProfileFixture.getOneDummyProfile();
        updatedDummyProfile = GithubUserProfileFixture.getOneUpdatedDummyProfile();
        dummyProfileEntity = GithubUserProfileFixture.getOneGithubUserProfileEntity();
        dummyUpdatedProfileEntity = GithubUserProfileFixture.getOneUpdatedGithubUserProfileEntity();
    }

    @Test
    public void shouldReturnMonoEmpty(){

        when(gitHubUserProfileRepository.findByGithubId(any())).thenReturn(Mono.empty());

        StepVerifier.create(gitHubUserProfileUpdateWhenChangedService.execute(dummyProfile))
                .verifyComplete();

    }

    @Test
    public void shouldThrowExceptionUserProfileRequired(){
        StepVerifier.create(gitHubUserProfileUpdateWhenChangedService.execute(null))
                .verifyErrorMatches(assertError(USER_PROFILE_REQUIRED));
    }

    @Test
    public void shouldReturnUpdatedGithubUserProfile(){

        when(gitHubUserProfileRepository.findByGithubId(any())).thenReturn(Mono.just(dummyProfileEntity));

        when(gitHubUserProfileRepository.save(any())).thenReturn(Mono.just(dummyUpdatedProfileEntity));

        StepVerifier.create(gitHubUserProfileUpdateWhenChangedService.execute(updatedDummyProfile))
                .expectNext(updatedDummyProfile)
                .verifyComplete();

    }
}