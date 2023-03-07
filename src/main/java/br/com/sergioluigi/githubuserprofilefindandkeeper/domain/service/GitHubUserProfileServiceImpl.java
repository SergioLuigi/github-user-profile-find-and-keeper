package br.com.sergioluigi.githubuserprofilefindandkeeper.domain.service;

import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.GithubUserProfile;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.client.GitHubFindUserProfileClient;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileFindAndKeeperException;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.repository.GitHubSaveUserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError.USERNAME_REQUIRED;
import static br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError.USER_PROFILE_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubUserProfileServiceImpl implements GitHubUserProfileService {

    private final GitHubFindUserProfileClient githubFindUserProfileClient;

    private final GitHubSaveUserProfileRepository gitHubSaveUserProfileRepository;

    private final GitHubUserProfileUpdateWhenChangedService gitHubUserProfileUpdateWhenChangedService;

    @Override
    public Mono<GithubUserProfile> findByUsername(String username){
        return  Mono.justOrEmpty(username)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new GitHubUserProfileFindAndKeeperException(USERNAME_REQUIRED))))
                .doOnNext(usern -> log.info("[GITHUB-USER-PROFILE-FIND-AND-KEEPER] - findByUsername - username = {}",usern))
                .flatMap(githubFindUserProfileClient::execute)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new GitHubUserProfileFindAndKeeperException(USER_PROFILE_NOT_FOUND))))
                .flatMap(githubUserProfileSource ->
                        gitHubUserProfileUpdateWhenChangedService.execute(githubUserProfileSource)
                        .switchIfEmpty(gitHubSaveUserProfileRepository.execute(githubUserProfileSource))
                )
                .doOnError(error -> log.error("[GITHUB-USER-PROFILE-FIND-AND-KEEPER] - findByUsername - error = {}", error.getMessage()))
                .doOnSuccess(response -> log.info("[GITHUB-USER-PROFILE-FIND-AND-KEEPER] - findByUsername - profile = {}", response));
    }


}
