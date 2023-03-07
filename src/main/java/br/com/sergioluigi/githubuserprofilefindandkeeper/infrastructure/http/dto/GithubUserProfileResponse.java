package br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.http.dto;

import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.GithubUserProfile;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GithubUserProfileResponse {

    @JsonProperty("login")
    private String login;

    @JsonProperty("id")
    private long id;

    @JsonProperty("node_id")
    private String nodeId;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("gravatar_id")
    private String gravatarId;

    @JsonProperty("url")
    private String url;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("followers_url")
    private String followersUrl;

    @JsonProperty("following_url")
    private String followingUrl;

    @JsonProperty("gists_url")
    private String gistsUrl;

    @JsonProperty("starred_url")
    private String starredUrl;

    @JsonProperty("subscriptions_url")
    private String subscriptionsUrl;

    @JsonProperty("organizations_url")
    private String organizationsUrl;

    @JsonProperty("repos_url")
    private String reposUrl;

    @JsonProperty("events_url")
    private String eventsUrl;

    @JsonProperty("received_events_url")
    private String receivedEventsUrl;

    @JsonProperty("type")
    private String type;

    @JsonProperty("site_admin")
    private boolean siteAdmin;

    @JsonProperty("name")
    private String name;

    @JsonProperty("company")
    private String company;

    @JsonProperty("blog")
    private String blog;

    @JsonProperty("location")
    private String location;

    @JsonProperty("email")
    private String email;

    @JsonProperty("hireable")
    private Boolean hireable;

    @JsonProperty("bio")
    private String bio;

    @JsonProperty("twitter_username")
    private String twitterUsername;

    @JsonProperty("public_repos")
    private Long publicRepos;

    @JsonProperty("public_gists")
    private Long publicGists;

    @JsonProperty("followers")
    private Long followers;

    @JsonProperty("following")
    private Long following;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;


    public GithubUserProfileResponse(GithubUserProfile githubUserProfile) {
        this.id = githubUserProfile.getId();
        this.login = githubUserProfile.getLogin();
        this.nodeId = githubUserProfile.getNodeId();
        this.avatarUrl = githubUserProfile.getAvatarUrl();
        this.gravatarId = githubUserProfile.getGravatarId();
        this.url = githubUserProfile.getUrl();
        this.htmlUrl = githubUserProfile.getHtmlUrl();
        this.followersUrl = githubUserProfile.getFollowersUrl();
        this.followingUrl = githubUserProfile.getFollowingUrl();
        this.gistsUrl = githubUserProfile.getGistsUrl();
        this.starredUrl = githubUserProfile.getStarredUrl();
        this.subscriptionsUrl = githubUserProfile.getSubscriptionsUrl();
        this.organizationsUrl = githubUserProfile.getOrganizationsUrl();
        this.reposUrl = githubUserProfile.getReposUrl();
        this.eventsUrl = githubUserProfile.getEventsUrl();
        this.receivedEventsUrl = githubUserProfile.getReceivedEventsUrl();
        this.type = githubUserProfile.getType();
        this.siteAdmin = githubUserProfile.isSiteAdmin();
        this.name = githubUserProfile.getName();
        this.company = githubUserProfile.getCompany();
        this.blog = githubUserProfile.getBlog();
        this.location = githubUserProfile.getLocation();
        this.email = githubUserProfile.getEmail();
        this.hireable = githubUserProfile.getHireable();
        this.bio = githubUserProfile.getBio();
        this.twitterUsername = githubUserProfile.getTwitterUsername();
        this.publicRepos = githubUserProfile.getPublicRepos();
        this.publicGists = githubUserProfile.getPublicGists();
        this.followers = githubUserProfile.getId();
        this.following = githubUserProfile.getFollowing();
        this.createdAt = githubUserProfile.getCreatedAt();
        this.updatedAt = githubUserProfile.getUpdatedAt();
    }

}