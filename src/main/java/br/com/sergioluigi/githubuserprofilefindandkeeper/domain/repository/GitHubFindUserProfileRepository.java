package br.com.sergioluigi.githubuserprofilefindandkeeper.domain.repository;

import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.GithubUserProfile;
import reactor.core.publisher.Mono;

public interface GitHubFindUserProfileRepository {
    Mono<GithubUserProfile> execute(GithubUserProfile githubUserProfile);
}
