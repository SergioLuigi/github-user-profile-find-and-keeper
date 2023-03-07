package br.com.sergioluigi.githubuserprofilefindandkeeper.domain.service;

import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.GithubUserProfile;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileFindAndKeeperException;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.entity.GithubUserProfileEntity;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.repository.GitHubUserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError.USER_PROFILE_REQUIRED;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubUserProfileUpdateWhenChangedServiceImpl implements GitHubUserProfileUpdateWhenChangedService{

    private final GitHubUserProfileRepository gitHubUserProfileRepository;

    @Override
    public Mono<GithubUserProfile> execute(GithubUserProfile githubUserProfileSource) {
        return Mono.justOrEmpty(githubUserProfileSource)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new GitHubUserProfileFindAndKeeperException(USER_PROFILE_REQUIRED))))
                .doOnNext(githubUserProfile -> log.info("[GITHUB-USER-PROFILE-FIND-AND-KEEPER] - updateWhenChanged - verifying if profile already saved - request = {}",githubUserProfileSource))
                .zipWhen(profile -> gitHubUserProfileRepository.findByGithubId(profile.getId()))
                .filter(tuple2 -> !tuple2.getT1().equals(tuple2.getT2().toModel()))
                .doOnNext(tuple2 -> log.info("[GITHUB-USER-PROFILE-FIND-AND-KEEPER] - updateWhenChanged - updating saved profile - githubId = {}",tuple2.getT2().getGithubId()))
                .map(tuple2 -> tuple2.getT2().updateWith(tuple2.getT1()))
                .flatMap(gitHubUserProfileRepository::save)
                .map(GithubUserProfileEntity::toModel)
                .doOnError(error -> log.error("[GITHUB-USER-PROFILE-FIND-AND-KEEPER] - updateWhenChanged - error = {}", error.getMessage()));
    }
}
