package com.docuffice.auth.domain.repository;

import com.docuffice.auth.infrastructure.persistence.entity.AuthenticationProviderEntity;
import io.smallrye.mutiny.Uni;

public interface AuthenticationProviderRepository {

    Uni<AuthenticationProviderEntity> findAuthenticationProviderById(Long id);

    Uni<AuthenticationProviderEntity> saveAuthenticationProvider(
        AuthenticationProviderEntity authenticationProvider);

    Uni<Boolean> deleteAuthenticationProviderById(Long id);

    Uni<AuthenticationProviderEntity> findAuthenticationProviderByName(String name);
}
