package br.com.sergioluigi.githubuserprofilefindandkeeper.fixture;

import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.GithubUserProfile;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.http.dto.GithubUserProfileResponse;
import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.entity.GithubUserProfileEntity;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public final class GithubUserProfileFixture {
    public static GithubUserProfile getOneDummyProfile(){
        return GithubUserProfile.builder()
                .login("SergioLuigi")
                .id(53507715L)
                .nodeId("MDQ6VXNlcjUzNTA3NzE1")
                .avatarUrl("https://avatars.githubusercontent.com/u/53507715?v=4")
                .gravatarId("")
                .url("https://api.github.com/users/SergioLuigi")
                .htmlUrl("https://github.com/SergioLuigi")
                .followersUrl("https://api.github.com/users/SergioLuigi/followers")
                .followingUrl("https://api.github.com/users/SergioLuigi/following{/other_user}")
                .gistsUrl("https://api.github.com/users/SergioLuigi/gists{/gist_id}")
                .starredUrl("https://api.github.com/users/SergioLuigi/starred{/owner}{/repo}")
                .subscriptionsUrl("https://api.github.com/users/SergioLuigi/starred{/owner}{/repo}")
                .organizationsUrl("https://api.github.com/users/SergioLuigi/orgs")
                .reposUrl("https://api.github.com/users/SergioLuigi/repos")
                .eventsUrl("https://api.github.com/users/SergioLuigi/events{/privacy}")
                .receivedEventsUrl("https://api.github.com/users/SergioLuigi/received_events")
                .type("User")
                .siteAdmin(false)
                .name("Sergio Luigi")
                .company(null)
                .blog("https://www.linkedin.com/in/sergio-luigi-alves/")
                .location("Rio de Janeiro - RJ")
                .email(null)
                .hireable(null)
                .bio("Desenvolvedor Java / Kotlin")
                .twitterUsername("SrgLuigi")
                .publicRepos(3L)
                .publicGists(0L)
                .followers(53507715L)
                .following(2L)
                .createdAt(LocalDateTime.parse("2019-07-31T06:11:47"))
                .updatedAt(LocalDateTime.parse("2023-03-04T19:03:05"))
                .build();
    }

    public static GithubUserProfileResponse getOneDummyProfileResponse(){
        return new GithubUserProfileResponse(getOneDummyProfile());
    }

    public static GithubUserProfileEntity getOneGithubUserProfileEntity(){
        return buildGitHubUserProfileEntity(getOneDummyProfile());
    }

    @NotNull
    private static GithubUserProfileEntity buildGitHubUserProfileEntity(GithubUserProfile githubUserProfile) {
        var entity = new GithubUserProfileEntity(githubUserProfile);
        entity.setId(1L);
        return entity;
    }


    public static GithubUserProfile getOneUpdatedDummyProfile() {
        return GithubUserProfile.builder()
                .login("SergioLuigi")
                .id(53507715L)
                .nodeId("MDQ6VXNlcjUzNTA3NzE1")
                .avatarUrl("https://avatars.githubusercontent.com/u/53507715?v=4")
                .gravatarId("")
                .url("https://api.github.com/users/SergioLuigi")
                .htmlUrl("https://github.com/SergioLuigi")
                .followersUrl("https://api.github.com/users/SergioLuigi/followers")
                .followingUrl("https://api.github.com/users/SergioLuigi/following{/other_user}")
                .gistsUrl("https://api.github.com/users/SergioLuigi/gists{/gist_id}")
                .starredUrl("https://api.github.com/users/SergioLuigi/starred{/owner}{/repo}")
                .subscriptionsUrl("https://api.github.com/users/SergioLuigi/starred{/owner}{/repo}")
                .organizationsUrl("https://api.github.com/users/SergioLuigi/orgs")
                .reposUrl("https://api.github.com/users/SergioLuigi/repos")
                .eventsUrl("https://api.github.com/users/SergioLuigi/events{/privacy}")
                .receivedEventsUrl("https://api.github.com/users/SergioLuigi/received_events")
                .type("User")
                .siteAdmin(false)
                .name("Sergio Luigi")
                .company(null)
                .blog("This data was changed")
                .location("Rio de Janeiro - RJ")
                .email(null)
                .hireable(null)
                .bio("Desenvolvedor Java / Kotlin")
                .twitterUsername("SrgLuigi")
                .publicRepos(3L)
                .publicGists(0L)
                .followers(53507715L)
                .following(2L)
                .createdAt(LocalDateTime.parse("2019-07-31T06:11:47"))
                .updatedAt(LocalDateTime.parse("2023-03-04T19:03:05"))
                .build();
    }

    public static GithubUserProfileEntity getOneUpdatedGithubUserProfileEntity() {
        return new GithubUserProfileEntity(getOneUpdatedDummyProfile());
    }
}
