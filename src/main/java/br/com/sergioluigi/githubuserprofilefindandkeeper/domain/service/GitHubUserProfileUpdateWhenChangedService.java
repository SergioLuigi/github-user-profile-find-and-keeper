package br.com.sergioluigi.githubuserprofilefindandkeeper.domain.service;

import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.GithubUserProfile;
import reactor.core.publisher.Mono;

public interface GitHubUserProfileUpdateWhenChangedService {
    Mono<GithubUserProfile> execute(GithubUserProfile githubUserProfileSource);
}
