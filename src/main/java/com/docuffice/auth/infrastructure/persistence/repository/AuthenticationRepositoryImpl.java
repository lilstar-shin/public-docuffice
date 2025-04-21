package com.docuffice.auth.infrastructure.persistence.repository;

import com.docuffice.auth.domain.repository.AuthenticationRepository;
import com.docuffice.auth.infrastructure.persistence.entity.AuthenticationEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthenticationRepositoryImpl implements PanacheRepository<AuthenticationEntity>,
    AuthenticationRepository {

    @Override
    public Uni<AuthenticationEntity> findAuthenticationById(Long id) {
        return findById(id);
    }


    @Override
    public Uni<AuthenticationEntity> saveAuthentication(AuthenticationEntity authentication) {
        return persist(authentication)
            .replaceWith(authentication);
    }

    @Override
    public Uni<AuthenticationEntity> updateAuthenticationForLogin(
        AuthenticationEntity authentication) {
        return update(
            "accessToken = : accessToken, " +
                "accessTokenExpiresAt = : accessTokenExpiresAt, " +
                "refreshToken = : refreshToken, " +
                "refreshTokenExpiresAt = : refreshTokenExpiresAt, " +
                "lastLoginAt = : lastLoginAt, " +
                "lastLoginIp = : lastLoginIp, " +
                "lastLoginUserAgent = : lastLoginUserAgent, " +
                "loginIp = : loginIp, " +
                "loginAt = : loginAt, " +
                "loginUserAgent = : loginUserAgent, " +
                "updatedAt = : updatedAt " +
                " where id = : id",
            Parameters.with(
                    "accessToken", authentication.getAccessToken())
                .and("accessTokenExpiresAt", authentication.getAccessTokenExpiresAt())
                .and("refreshToken", authentication.getRefreshToken())
                .and("refreshTokenExpiresAt", authentication.getRefreshTokenExpiresAt())
                .and("lastLoginAt", authentication.getLastLoginAt())
                .and("lastLoginIp", authentication.getLastLoginIp())
                .and("lastLoginUserAgent", authentication.getLastLoginUserAgent())
                .and("loginIp", authentication.getLoginIp())
                .and("loginAt", authentication.getLoginAt())
                .and("loginUserAgent", authentication.getLoginUserAgent())
                .and("updatedAt", authentication.getUpdatedAt())
                .and("id", authentication.getId())
        )
            .replaceWith(authentication);
    }

    @Override
    public Uni<Boolean> deleteAuthenticationById(Long id) {
        return deleteById(id);
    }

    @Override
    public Uni<AuthenticationEntity> findAuthenticationByProviderUserIdAndProviderId(
        String providerUserId,
        Long providerId) {
        return find(
            "SELECT a " +
                "FROM AuthenticationEntity a " +
                "  LEFT JOIN FETCH a.userAuthentication ua " +
                "WHERE a.providerUserId = ?1 " +
                "  AND a.authenticationProviderId = ?2",
            providerUserId, providerId
        )
            .firstResult();
    }
}
