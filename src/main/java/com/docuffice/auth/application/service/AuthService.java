package com.docuffice.auth.application.service;

import com.docuffice.auth.application.dto.request.GoogleCallbackRequestDTO;
import com.docuffice.auth.application.dto.response.GoogleTokenResponseDTO;
import com.docuffice.auth.application.dto.response.GoogleUserInfoResponseDTO;
import com.docuffice.auth.application.dto.response.UserLoginResponseDTO;
import com.docuffice.auth.application.mapper.AuthApplicationMapper;
import com.docuffice.auth.application.port.out.GoogleTokenClient;
import com.docuffice.auth.application.port.out.GoogleUserInfoClient;
import com.docuffice.auth.domain.model.Authentication;
import com.docuffice.auth.domain.model.AuthenticationProvider;
import com.docuffice.auth.domain.model.Token;
import com.docuffice.auth.domain.service.AuthDomainService;
import com.docuffice.user.interfaces.exception.OAuthException;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.InternalServerErrorException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Slf4j
@ApplicationScoped
public class AuthService {

    @ConfigProperty(name = "oauth.google.client.id")
    String googleClientId;

    @ConfigProperty(name = "oauth.google.client.secret")
    String googleClientSecret;

    @Inject
    @RestClient
    GoogleTokenClient googleTokenClient;

    @Inject
    @RestClient
    GoogleUserInfoClient googleUserInfoClient;

    @Inject
    AuthDomainService authDomainService;

    @Inject
    AuthApplicationMapper authApplicationMapper;

    @Inject
    TokenService tokenService;


    @WithSession
    public Uni<UserLoginResponseDTO> googleOAuthLogin(GoogleCallbackRequestDTO dto,
        RoutingContext ctx) {

        // exchange code for access token
        Uni<String> accessTokenUni = googleExchangeCodeForTokenResponse(
            dto.getCode(),
            googleClientId,
            googleClientSecret,
            dto.getRedirectUri()
        ).map(GoogleTokenResponseDTO::accessToken);

        // find database in authentication_provider table
        Uni<Long> oauthProviderId = authDomainService
            .findAuthenticationProviderIdByName(AuthenticationProvider.GOOGLE.name());

        // fetch user info using access token
        Uni<GoogleUserInfoResponseDTO> userInfo = accessTokenUni
            .flatMap(this::googleFetchUserInfo);

        // authentication find or insert
        return Uni.combine()
            .all()
            .unis(userInfo, oauthProviderId)
            .asTuple()
            .map(userInfoAndProvider -> authApplicationMapper.googleUserInfoResponseToOAuth(
                userInfoAndProvider.getItem1(), userInfoAndProvider.getItem2()))
            .flatMap(authDomainService::authenticationFindOrInsertByOAuth)
            .flatMap(authentication -> authenticationUpdateForLogin(authentication, ctx))
            .map(authApplicationMapper::authenticationToUserLoginResponseDTO);
    }

    private Uni<GoogleTokenResponseDTO> googleExchangeCodeForTokenResponse(String code,
        String clientId, String clientSecret, String redirectUri) {
        return googleTokenClient.exchangeCode(code, clientId, clientSecret, redirectUri,
                "authorization_code")
            .onFailure()
            .transform(failure -> {
                log.error("Failed to exchange Google code for access token", failure);
                return new OAuthException("Failed to exchange Google code for access token");
            });
    }

    private Uni<GoogleUserInfoResponseDTO> googleFetchUserInfo(String accessToken) {
        if (!accessToken.startsWith("Bearer ")) {
            accessToken = "Bearer " + accessToken;
        }
        return googleUserInfoClient.fetchUserInfo(accessToken)
            .onFailure()
            .transform(failure -> {
                log.error("Failed to fetch Google user info with token", failure);
                return new OAuthException("Failed to fetch Google user info");
            });
    }

    private Uni<Authentication> authenticationUpdateForLogin(Authentication authentication,
        RoutingContext ctx) {
        Token token = authentication.getUserRegistered()
            ? tokenService.generateTokens(authentication.getId())
            : tokenService.generateTemporaryTokens(authentication.getId());
        authentication.setAccessToken(token.getAccessToken());
        authentication.setAccessTokenExpiresAt(token.getAccessTokenExpiresAt());
        authentication.setRefreshToken(token.getRefreshToken());
        authentication.setRefreshTokenExpiresAt(token.getRefreshTokenExpiresAt());
        authentication.setLastLoginAt(authentication.getLoginAt());
        authentication.setLastLoginIp(authentication.getLoginIp());
        authentication.setLastLoginUserAgent(authentication.getLoginUserAgent());
        authentication.setLoginAt(LocalDateTime.now());
        authentication.setLoginIp(ctx.request().remoteAddress().host());
        authentication.setLoginUserAgent(ctx.request().getHeader("User-Agent"));
        authentication.setUpdatedAt(LocalDateTime.now());
        return authDomainService.updateAuthenticationForLogin(authentication)
            .onFailure()
            .transform(failure -> {
                log.error("Failed to update authentication for login", failure);
                return new InternalServerErrorException(
                    "Failed to update authentication for login");
            })
            .invoke(auth -> auth.setUserRegistered(authentication.getUserRegistered()));
    }
}
