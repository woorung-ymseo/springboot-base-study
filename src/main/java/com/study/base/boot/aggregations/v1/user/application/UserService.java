package com.study.base.boot.aggregations.v1.user.application;

import com.study.base.boot.aggregations.v1.auth.application.AuthorizationService;
import com.study.base.boot.aggregations.v1.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final AuthorizationService authorizationService;

    public String login(final String userId, final String password) {
        final long id = 1L;
        final String accessToken = authorizationService.createAccessToken(id);

        authorizationService.createRefreshToken();

        return accessToken;
    }

    public User get(long id) {
        Collection<GrantedAuthority> roles =  List.of(() ->  "ROLE_" + "USER");

        return User.builder()
                .id(id)
                .userId("omgar")
                .userName("서영민")
                .roles(roles.stream().toList())
                .build();
    }
}
