package br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.entity;

import br.com.sergioluigi.githubuserprofilefindandkeeper.domain.GithubUserProfile;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Table(name="github_user_profile")
public class GithubUserProfileEntity {

    @Id
    private Long id;

    @Column("github_id")
    private Long githubId;

    private String login;

    private String nodeId;

    private String avatarUrl;

    private String gravatarId;

    private String url;

    private String htmlUrl;

    private String followersUrl;

    private String followingUrl;

    private String gistsUrl;

    private String starredUrl;

    private String subscriptionsUrl;

    private String organizationsUrl;

    private String reposUrl;

    private String eventsUrl;

    private String receivedEventsUrl;

    private String type;

    private boolean siteAdmin;

    private String name;

    private String company;

    private String blog;

    private String location;

    private String email;

    private Boolean hireable;

    private String bio;

    private String twitterUsername;

    private Long publicRepos;

    private Long publicGists;

    private Long followers;

    private Long following;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public GithubUserProfileEntity(GithubUserProfile githubUserProfile) {
        this.githubId = githubUserProfile.getId();
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

    public GithubUserProfileEntity updateWith(GithubUserProfile githubUserProfile){

        return GithubUserProfileEntity.builder()
                .id(id)
                .githubId(githubUserProfile.getId())
                .login(githubUserProfile.getLogin())
                .nodeId(githubUserProfile.getNodeId())
                .avatarUrl(githubUserProfile.getAvatarUrl())
                .gravatarId(githubUserProfile.getGravatarId())
                .url(githubUserProfile.getUrl())
                .htmlUrl(githubUserProfile.getHtmlUrl())
                .followersUrl(githubUserProfile.getFollowersUrl())
                .followingUrl(githubUserProfile.getFollowingUrl())
                .gistsUrl(githubUserProfile.getGistsUrl())
                .starredUrl(githubUserProfile.getStarredUrl())
                .subscriptionsUrl(githubUserProfile.getSubscriptionsUrl())
                .organizationsUrl(githubUserProfile.getOrganizationsUrl())
                .reposUrl(githubUserProfile.getReposUrl())
                .eventsUrl(githubUserProfile.getEventsUrl())
                .receivedEventsUrl(githubUserProfile.getReceivedEventsUrl())
                .type(githubUserProfile.getType())
                .siteAdmin(githubUserProfile.isSiteAdmin())
                .name(githubUserProfile.getName())
                .company(githubUserProfile.getCompany())
                .blog(githubUserProfile.getBlog())
                .location(githubUserProfile.getLocation())
                .email(githubUserProfile.getEmail())
                .hireable(githubUserProfile.getHireable())
                .bio(githubUserProfile.getBio())
                .twitterUsername(githubUserProfile.getTwitterUsername())
                .publicRepos(githubUserProfile.getPublicRepos())
                .publicGists(githubUserProfile.getPublicGists())
                .followers(githubUserProfile.getFollowers())
                .following(githubUserProfile.getFollowing())
                .createdAt(githubUserProfile.getCreatedAt())
                .updatedAt(githubUserProfile.getUpdatedAt())
                .build();
    }

    public GithubUserProfile toModel(){
        return GithubUserProfile.builder()
                .id(githubId)
                .login(login)
                .nodeId(nodeId)
                .avatarUrl(avatarUrl)
                .gravatarId(gravatarId)
                .url(url)
                .htmlUrl(htmlUrl)
                .followersUrl(followersUrl)
                .followingUrl(followingUrl)
                .gistsUrl(gistsUrl)
                .starredUrl(starredUrl)
                .subscriptionsUrl(starredUrl)
                .organizationsUrl(organizationsUrl)
                .reposUrl(reposUrl)
                .eventsUrl(eventsUrl)
                .receivedEventsUrl(receivedEventsUrl)
                .type(type)
                .siteAdmin(siteAdmin)
                .name(name)
                .company(company)
                .blog(blog)
                .location(location)
                .email(email)
                .hireable(hireable)
                .bio(bio)
                .twitterUsername(twitterUsername)
                .publicRepos(publicRepos)
                .publicGists(publicGists)
                .followers(followers)
                .following(following)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}