package br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.repository;

import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.GithubUserProfile;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileFindAndKeeperException;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.repository.GitHubFindUserProfileRepository;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.entity.GithubUserProfileEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError.USER_PROFILE_REQUIRED;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GitHubFindUserProfileByGitHubIdRepositoryImpl implements GitHubFindUserProfileRepository {

    private final GitHubUserProfileRepository gitHubUserProfileRepository;

    @Override
    public Mono<GithubUserProfile> execute(GithubUserProfile githubUserProfile) {
        return Mono.justOrEmpty(githubUserProfile)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new GitHubUserProfileFindAndKeeperException(USER_PROFILE_REQUIRED))))
                .flatMap(profile -> gitHubUserProfileRepository.findByGithubId(profile.getId()))
                .map(GithubUserProfileEntity::toModel)
                .doOnError(error -> log.error("[GITHUB-USER-PROFILE-FIND-AND-KEEPER] - findUserProfileByGithubId - error = {}", error.getMessage()))
                .doOnSuccess(response -> log.info("[GITHUB-USER-PROFILE-FIND-AND-KEEPER] - findUserProfileByGithubId - profile = {}", response));
    }
}
