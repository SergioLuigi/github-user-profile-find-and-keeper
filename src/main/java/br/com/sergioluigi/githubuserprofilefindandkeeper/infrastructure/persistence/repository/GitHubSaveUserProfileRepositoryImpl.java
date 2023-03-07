package br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.repository;

import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.GithubUserProfile;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileFindAndKeeperException;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.repository.GitHubSaveUserProfileRepository;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.entity.GithubUserProfileEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError.USER_PROFILE_REQUIRED;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GitHubSaveUserProfileRepositoryImpl implements GitHubSaveUserProfileRepository {

    private final GitHubUserProfileRepository gitHubUserProfileRepository;

    @Override
    public Mono<GithubUserProfile> execute(GithubUserProfile githubUserProfile) {
        return Mono.justOrEmpty(githubUserProfile)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new GitHubUserProfileFindAndKeeperException(USER_PROFILE_REQUIRED))))
                .map(GithubUserProfileEntity::new)
                .doOnNext(githubUserProfileEntity ->  log.info("[GITHUB-USER-PROFILE-FIND-AND-KEEPER] - saving profile = {}", githubUserProfile))
                .flatMap(gitHubUserProfileRepository::save)
                .map(GithubUserProfileEntity::toModel)
                .doOnError(error -> log.error("[GITHUB-USER-PROFILE-FIND-AND-KEEPER] - save profile - error = {}", error.getMessage()))
                .doOnSuccess(response -> log.info("[GITHUB-USER-PROFILE-FIND-AND-KEEPER] - profile saved = {}", response));
    }
}
