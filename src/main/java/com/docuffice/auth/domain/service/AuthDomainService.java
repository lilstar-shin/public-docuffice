package com.docuffice.auth.domain.service;

import com.docuffice.auth.domain.mapper.AuthDomainMapper;
import com.docuffice.auth.domain.model.Authentication;
import com.docuffice.auth.domain.model.OAuth;
import com.docuffice.auth.domain.repository.AuthenticationProviderRepository;
import com.docuffice.auth.domain.repository.AuthenticationRepository;
import com.docuffice.auth.infrastructure.persistence.entity.AuthenticationProviderEntity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class AuthDomainService {

    @Inject
    AuthenticationRepository authenticationRepository;

    @Inject
    AuthenticationProviderRepository authenticationProviderRepository;

    @Inject
    AuthDomainMapper authDomainMapper;


    public Uni<Authentication> findAuthenticationById(Long id) {
        return authenticationRepository.findAuthenticationById(id)
            .map(authDomainMapper::authenticationEntityToAuthenticationModel);
    }

    public Uni<Authentication> createAuthentication(Authentication authentication) {
        return authenticationRepository.saveAuthentication(
                authDomainMapper.authenticationModelToAuthenticationEntity(authentication))
            .map(authDomainMapper::authenticationEntityToAuthenticationModel);
    }

    public Uni<Long> findAuthenticationProviderIdByName(String name) {
        return authenticationProviderRepository
            .findAuthenticationProviderByName(name)
            .map(AuthenticationProviderEntity::getId);
    }

    public Uni<Authentication> authenticationFindOrInsertByOAuth(OAuth oauth) {
        return authenticationRepository
            .findAuthenticationByProviderUserIdAndProviderId(oauth.getProviderUserId(),
                oauth.getProviderId())
            .onItem().ifNull()
            .continueWith(authDomainMapper.oauthModelToAuthenticationEntity(oauth))
            .flatMap(authenticationRepository::saveAuthentication)
            .map(authEntity -> {
                Authentication authModel = authDomainMapper.authenticationEntityToAuthenticationModel(
                    authEntity);
                authModel.setUserRegistered(authEntity.getUserAuthentication() != null);
                return authModel;
            });
    }

    public Uni<Authentication> updateAuthenticationForLogin(Authentication authentication) {
        return authenticationRepository
            .updateAuthenticationForLogin(
                authDomainMapper.authenticationModelToAuthenticationEntity(authentication))
            .map(authDomainMapper::authenticationEntityToAuthenticationModel);
    }
}
