package br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception;

import lombok.Getter;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class GitHubUserProfileFindAndKeeperException extends ResponseStatusException {

    private final GitHubUserProfileError gitHubUserProfileError;

    public GitHubUserProfileFindAndKeeperException(GitHubUserProfileError gitHubUserProfileError) {
        super(gitHubUserProfileError.getHttpStatus(), gitHubUserProfileError.getReason());
        this.gitHubUserProfileError = gitHubUserProfileError;
    }
}
