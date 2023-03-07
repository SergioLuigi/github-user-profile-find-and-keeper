package br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.http.github;

import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.GithubUserProfile;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.client.GitHubFindUserProfileClient;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileFindAndKeeperException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError.USER_PROFILE_INTERNAL_SERVER_ERROR;
import static br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError.USER_PROFILE_NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class GitHubFindUserProfileClientImpl implements GitHubFindUserProfileClient {

    private final WebClient webClient;

    @Override
    public Mono<GithubUserProfile> execute(String username) {
        log.info("[GITHUB-USER-PROFILE-FIND-AND-KEEPER] - trying to find profile in api.github by username = {}",username);
        return webClient.get()
                .uri("/{user}",username)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.value() == 404, error -> Mono.error(new GitHubUserProfileFindAndKeeperException(USER_PROFILE_NOT_FOUND)))
                .onStatus(httpStatusCode -> httpStatusCode.value() != 200, error -> Mono.error(new GitHubUserProfileFindAndKeeperException(USER_PROFILE_INTERNAL_SERVER_ERROR)))
                .bodyToMono(GithubUserProfile.class)
                .doOnError(error -> log.error("[GITHUB-USER-PROFILE-FIND-AND-KEEPER] - findUserProfileClient - error = {}", error.getMessage()))
                .doOnSuccess(response -> log.info("[GITHUB-USER-PROFILE-FIND-AND-KEEPER] - findUserProfileClient - profile found = {}", response));

    }


}
