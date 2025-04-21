package com.docuffice.auth.domain.model;


import lombok.Data;

@Data
public class OAuth {

    private AuthenticationProvider authenticationProvider;

    private Long providerId;

    private String providerUserId;

    private String providerUserName;

    private String providerUserEmail;
}
