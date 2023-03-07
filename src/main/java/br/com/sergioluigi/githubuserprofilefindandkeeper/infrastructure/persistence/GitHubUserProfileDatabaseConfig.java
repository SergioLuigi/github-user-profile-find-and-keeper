package br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories
public class GitHubUserProfileDatabaseConfig {
}
