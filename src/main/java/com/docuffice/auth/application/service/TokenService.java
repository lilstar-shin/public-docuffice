package com.docuffice.auth.application.service;

import com.docuffice.auth.domain.model.Token;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Slf4j
@ApplicationScoped
public class TokenService {

    @ConfigProperty(name = "mp.jwt.duration.access-token.max-age")
    Long accessTokenMaxAge;

    @ConfigProperty(name = "mp.jwt.duration.refresh-token.max-age")
    Long refreshTokenMaxAge;

    @ConfigProperty(name = "mp.jwt.duration.temporary-token.max-age")
    Long temporaryTokenMaxAge;

    @ConfigProperty(name = "mp.jwt.verify.public-key.location")
    String publicKeyLocation;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    public String generateAccessToken(Long authenticationId) {
        return Jwt.issuer(issuer)
            .upn(authenticationId.toString())
            .claim("userRegistered", true)
            .expiresIn(accessTokenMaxAge)
            .sign();
    }

    public String generateRefreshToken(Long authenticationId) {
        return Jwt.issuer(issuer)
            .upn(authenticationId.toString())
            .claim("userRegistered", true)
            .expiresIn(refreshTokenMaxAge)
            .sign();
    }

    public String generateTemporaryToken(Long authenticationId) {
        return Jwt.issuer(issuer)
            .upn(authenticationId.toString())
            .claim("userRegistered", false)
            .expiresIn(temporaryTokenMaxAge)
            .sign();
    }

    public Token generateTokens(Long authenticationId) {
        String accessToken = generateAccessToken(authenticationId);
        LocalDateTime accessTokenExpiresAt = LocalDateTime.now().plusSeconds(accessTokenMaxAge);
        String refreshToken = generateRefreshToken(authenticationId);
        LocalDateTime refreshTokenExpiresAt = LocalDateTime.now().plusSeconds(refreshTokenMaxAge);

        return Token.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .accessTokenExpiresAt(accessTokenExpiresAt)
            .refreshTokenExpiresAt(refreshTokenExpiresAt)
            .build();
    }

    public Token generateTemporaryTokens(Long authenticationId) {
        String accessToken = generateTemporaryToken(authenticationId);
        LocalDateTime accessTokenExpiresAt = LocalDateTime.now().plusSeconds(temporaryTokenMaxAge);

        return Token.builder()
            .accessToken(accessToken)
            .refreshToken(null)
            .accessTokenExpiresAt(accessTokenExpiresAt)
            .refreshTokenExpiresAt(null)
            .build();
    }
}
