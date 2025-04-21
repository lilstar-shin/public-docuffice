package com.docuffice.auth.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthenticationProvider {
    GOOGLE("GOOGLE"),
    KAKAO("KAKAO"),
    NAVER("NAVER");

    private final String code;

    public static AuthenticationProvider fromString(String provider) {
        for (AuthenticationProvider authProvider : AuthenticationProvider.values()) {
            if (authProvider.code.equalsIgnoreCase(provider)) {
                return authProvider;
            }
        }
        throw new IllegalArgumentException("No constant with text " + provider + " found");
    }
}
