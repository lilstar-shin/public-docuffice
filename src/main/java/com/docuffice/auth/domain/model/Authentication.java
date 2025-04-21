package com.docuffice.auth.domain.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Authentication {

    private Long id;

    private Long authenticationProviderId;

    private String providerUserId;

    private String providerUserName;

    private String providerUserEmail;

    private String password;

    private String accessToken;

    private LocalDateTime accessTokenExpiresAt;

    private String refreshToken;

    private LocalDateTime refreshTokenExpiresAt;

    private LocalDateTime loginAt;

    private String loginIp;

    private String loginUserAgent;

    private LocalDateTime lastLoginAt;

    private String lastLoginIp;

    private String lastLoginUserAgent;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /* Join Column */
    private Boolean userRegistered;
}
