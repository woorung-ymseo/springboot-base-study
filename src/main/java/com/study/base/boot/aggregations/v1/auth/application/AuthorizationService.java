package com.study.base.boot.aggregations.v1.auth.application;

import com.study.base.boot.aggregations.v1.auth.infrastructure.redis.entity.RefreshToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationService {

    @Value("${jwt.access-token.key}")
    private String accessKey;

    private final TokenProvider tokenProvider;

    public String createAccessToken(long id) {
        final String issuer = "BASE"; // ACCESS TOKEN 생성시 보통 서버 명을 넣는다.
        final String subject = "access"; // access token 이므로 제목을 access 로준다.
        final String audience = "1"; // 발급 대상 (로그인 유저의 pk)
//        final Date expiredAt = Date.from(Instant.now().plus(Duration.ofSeconds(1L))); // 1초
        final Date expiredAt = Date.from(Instant.now().plus(Duration.ofDays(1L))); // 1일
        final Date notBeforeAt = Date.from(Instant.now()); // 토큰 발급 시점부터 사용 가능
        final Date issuedAt = Date.from(Instant.now()); // 발급 시간
        final String jwtId = UUID.randomUUID().toString(); // jwt 식별 id
        final SecretKey signatureKey = Keys.hmacShaKeyFor(
                Base64.getEncoder()
                        .encodeToString(accessKey.getBytes())
                        .getBytes()
        );


        final String accessToken = Jwts.builder()
                .setIssuer(issuer)        // 발급자 (iss)
                .setSubject(subject)       // 제목 (sub)
                .setAudience(audience)      // 발급 대상 (aud)
                .setExpiration(expiredAt)    // 만료시간 (exp)
                .setNotBefore(notBeforeAt)     // 토큰 활성 날짜 (nbf)
                .setIssuedAt(issuedAt)      // 발급 시간 (iat)
                .setId(jwtId)            // jwt 식별자 (jti)
                .signWith(signatureKey)         // 서명 키
                .compact();

        return accessToken;
    }

    public String createRefreshToken() {

        return tokenProvider.createRefreshToken();
    }

    public String refresh(String refreshToken) {
        final var refreshTokenOptional = tokenProvider.get(refreshToken);
        String accessToken = null;

        if (refreshTokenOptional.isPresent()) {
            accessToken = this.createAccessToken(1);
        } else{
            // TODO: 2023/11/18 로그아웃 익셉션
        }

        return accessToken;
    }
}
