package br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.http.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@Configuration
public class GitHubUserProfileHttpConfig {

    @Bean
    public WebClient webClient(@Value("${github.api}") String gitHubApi){

        return WebClient.create(gitHubApi);
    }
}
