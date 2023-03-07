package br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.http.api.controller;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileFindAndKeeperException;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.service.GitHubUserProfileService;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.http.dto.GithubUserProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError.USER_PROFILE_REQUIRED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class GithubUserController {

    private final GitHubUserProfileService gitHubUserProfileService;

    @ResponseStatus(OK)
    @GetMapping("/{username}")
    private Mono<GithubUserProfileResponse> getProfileByUserName(@PathVariable String username){
        return Mono.justOrEmpty(username)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new GitHubUserProfileFindAndKeeperException(USER_PROFILE_REQUIRED))))
                .doOnNext(usname -> log.info("[GITHUB-USER-PROFILE-FIND-AND-KEEPER] - getProfileByUserName - username = {}", usname))
                .flatMap(gitHubUserProfileService::findByUsername)
                .map(GithubUserProfileResponse::new)
                .doOnError(error -> log.error("[GITHUB-USER-PROFILE-FIND-AND-KEEPER] - getProfileByUserName - error = {}", error.getMessage()))
                .doOnSuccess(response -> log.info("[GITHUB-USER-PROFILE-FIND-AND-KEEPER] - getProfileByUserName - profile = {}", response));
    }

}
