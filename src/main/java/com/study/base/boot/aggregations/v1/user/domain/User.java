package com.study.base.boot.aggregations.v1.user.domain;

import com.study.base.boot.config.security.dto.AuthUserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class User {

    private long id;
    private String userId;
    private String userName;
    private List<GrantedAuthority> roles;

    public AuthUserInfo toAuthUser() {
        return AuthUserInfo.builder()
                .id(id)
                .userId(userId)
                .userName(userName)
                .roles(roles)
                .build();
    }
}
