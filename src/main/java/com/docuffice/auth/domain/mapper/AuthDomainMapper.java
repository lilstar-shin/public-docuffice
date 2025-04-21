package com.docuffice.auth.domain.mapper;

import com.docuffice.auth.domain.model.Authentication;
import com.docuffice.auth.domain.model.OAuth;
import com.docuffice.auth.infrastructure.persistence.entity.AuthenticationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface AuthDomainMapper {

    /*
     * Entity -> Model
     */
    @Mapping(target = "userRegistered", ignore = true)
    Authentication authenticationEntityToAuthenticationModel(AuthenticationEntity entity);

    /**
     * Model -> Entity
     */
    @Mapping(target = "userAuthentication", ignore = true)
    AuthenticationEntity authenticationModelToAuthenticationEntity(Authentication model);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authenticationProviderId", source = "providerId")
    @Mapping(target = "providerUserId", source = "providerUserId")
    @Mapping(target = "providerUserName", source = "providerUserName")
    @Mapping(target = "providerUserEmail", source = "providerUserEmail")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "accessToken", ignore = true)
    @Mapping(target = "accessTokenExpiresAt", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "refreshTokenExpiresAt", ignore = true)
    @Mapping(target = "loginAt", ignore = true)
    @Mapping(target = "loginIp", ignore = true)
    @Mapping(target = "loginUserAgent", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    @Mapping(target = "lastLoginIp", ignore = true)
    @Mapping(target = "lastLoginUserAgent", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "userAuthentication", ignore = true)
    AuthenticationEntity oauthModelToAuthenticationEntity(OAuth model);
    
}
