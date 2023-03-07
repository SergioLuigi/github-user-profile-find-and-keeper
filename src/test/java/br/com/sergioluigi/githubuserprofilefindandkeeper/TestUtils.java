package br.com.sergioluigi.githubuserprofilefindandkeeper;

import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileError;
import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception.GitHubUserProfileFindAndKeeperException;

import java.util.function.Predicate;

public class TestUtils {
    public static Predicate<Throwable> assertError(GitHubUserProfileError gitHubUserProfileError){
        return throwable -> throwable instanceof GitHubUserProfileFindAndKeeperException ex &&
                ex.getGitHubUserProfileError().equals(gitHubUserProfileError);
    }
}
