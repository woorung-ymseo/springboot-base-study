package com.study.base.boot.aggregations.v1.auth.application;

import com.study.base.boot.aggregations.v1.user.application.UserService;
import com.study.base.boot.aggregations.v1.user.domain.User;
import com.study.base.boot.config.security.dto.AuthUserInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Properties;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Import(Properties.class)
@Slf4j
public class AuthenticationService {

    @Value("${jwt.access-token.key}")
    private String accessKey;

    private final UserService userService;

    public boolean doAuthentication(String accessToken) {
        boolean isAuth = false;

        if (StringUtils.isNotEmpty(accessToken)) {
            final var claims = this.getClaims(accessToken);

            if (claims != null && claims.getAudience().size() > 0) {
                isAuth = true;

                final Set<String> audience = claims.getAudience();
                final String id = audience.stream().toList().get(0);
                final User user = userService.get(Long.valueOf(id));
                AuthUserInfo authUserInfo = AuthUserInfo.builder().build();

                if (user != null) {
                    authUserInfo = user.toAuthUser();

                }

                this.setAuthentication(authUserInfo);

                log.info("audience : {}", id);
            }
        }

        return isAuth;
    }

    public Claims getClaims(String accessToken) {
        Claims claims = null;
        final SecretKey signatureKey = Keys.hmacShaKeyFor(
                Base64.getEncoder()
                        .encodeToString(accessKey.getBytes())
                        .getBytes()
        );

        try {
            claims = Jwts.parser()
                    .setSigningKey(signatureKey)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            return claims;
        }
    }

    public boolean isNeedRefresh(String accessToken) {
        boolean isExpired = false;
        final SecretKey signatureKey = Keys.hmacShaKeyFor(
                Base64.getEncoder()
                        .encodeToString(accessKey.getBytes())
                        .getBytes()
        );

        try {
            Jwts.parser()
                    .setSigningKey(signatureKey)
                    .build()
                    .parseSignedClaims(accessToken);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();

            isExpired = true;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return isExpired;
    }

    public void setAuthentication(UserDetails userDetails) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}


