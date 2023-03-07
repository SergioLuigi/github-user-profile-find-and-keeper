package br.com.sergioluigi.githubuserprofilefindandkeeper.domain.client;

import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.GithubUserProfile;
import reactor.core.publisher.Mono;

public interface GitHubFindUserProfileClient {

    Mono<GithubUserProfile> execute(String username);
}
