package com.study.base.boot.config.security;

import com.study.base.boot.aggregations.v1.auth.application.AuthenticationService;
import com.study.base.boot.config.constants.HeaderConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {


    private final AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String accessToken = response.getHeader(HeaderConstants.X_ACCESS_TOKEN);

        log.info("===> accessToken :: {}", accessToken);

        authenticationService.doAuthentication(accessToken);

        filterChain.doFilter(request, response);
    }
}
