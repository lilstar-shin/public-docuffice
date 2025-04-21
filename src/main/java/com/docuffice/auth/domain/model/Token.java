package com.docuffice.auth.domain.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Token {

    String accessToken;

    String refreshToken;

    LocalDateTime accessTokenExpiresAt;

    LocalDateTime refreshTokenExpiresAt;
}
