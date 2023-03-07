package br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.repository;


import br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.persistence.entity.GithubUserProfileEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface GitHubUserProfileRepository extends R2dbcRepository<GithubUserProfileEntity, Long> {
    Mono<GithubUserProfileEntity> findByGithubId(Long id);
}
