package com.study.base.boot.aggregations.v1.auth.infrastructure.redis.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@Getter
@Builder
@RedisHash(value = "refresh-token", timeToLive = 60 * 60 * 24)
public class RefreshToken {

    @Id
    private String id;

    @Indexed
    private String token;
    private List<String> role;

}
