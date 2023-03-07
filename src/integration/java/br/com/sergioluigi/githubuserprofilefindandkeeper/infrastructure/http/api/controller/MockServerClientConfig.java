package br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.http.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;


@TestConfiguration
public class MockServerClientConfig {
    @Bean
    @Primary
    public WebClient webClient(@Value("${github.api}") String gitHubApi){

        return WebClient.create(gitHubApi);
    }
}
