package com.docuffice.auth.domain.repository;

import com.docuffice.auth.infrastructure.persistence.entity.AuthenticationEntity;
import io.smallrye.mutiny.Uni;

public interface AuthenticationRepository {

    Uni<AuthenticationEntity> findAuthenticationById(Long id);

    Uni<AuthenticationEntity> saveAuthentication(AuthenticationEntity authentication);

    Uni<AuthenticationEntity> updateAuthenticationForLogin(AuthenticationEntity authentication);

    Uni<Boolean> deleteAuthenticationById(Long id);

    Uni<AuthenticationEntity> findAuthenticationByProviderUserIdAndProviderId(
        String providerUserId,
        Long providerId);
}
