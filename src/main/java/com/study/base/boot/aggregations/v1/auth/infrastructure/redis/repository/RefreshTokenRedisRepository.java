package com.study.base.boot.aggregations.v1.auth.infrastructure.redis.repository;

import com.study.base.boot.aggregations.v1.auth.infrastructure.redis.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

}
