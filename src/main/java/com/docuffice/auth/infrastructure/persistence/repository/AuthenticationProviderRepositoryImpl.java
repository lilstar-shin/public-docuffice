package com.docuffice.auth.infrastructure.persistence.repository;

import com.docuffice.auth.domain.repository.AuthenticationProviderRepository;
import com.docuffice.auth.infrastructure.persistence.entity.AuthenticationProviderEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthenticationProviderRepositoryImpl implements
    PanacheRepository<AuthenticationProviderEntity>, AuthenticationProviderRepository {

    @Override
    public Uni<AuthenticationProviderEntity> findAuthenticationProviderById(Long id) {
        return findById(id);
    }

    @Override
    public Uni<AuthenticationProviderEntity> saveAuthenticationProvider(
        AuthenticationProviderEntity authenticationProvider) {
        return persist(authenticationProvider);
    }

    @Override
    public Uni<Boolean> deleteAuthenticationProviderById(Long id) {
        return deleteById(id);
    }

    @Override
    public Uni<AuthenticationProviderEntity> findAuthenticationProviderByName(String name) {
        return find("name", name).firstResult();
    }
}
