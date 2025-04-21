package com.docuffice.auth.application.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserLoginResponseDTO {

    private String accessToken;
    private LocalDateTime accessTokenExpiresAt;
    private String refreshToken;
    private LocalDateTime refreshTokenExpiresAt;
    private Boolean userRegistered;

}
