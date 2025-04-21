package com.docuffice.auth.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleTokenResponseDTO(
    @JsonProperty("id_token")
    String idToken,

    @JsonProperty("access_token")
    String accessToken,

    @JsonProperty("refresh_token")
    String refreshToken,

    @JsonProperty("scope")
    String scope,

    @JsonProperty("token_type")
    String tokenType,

    @JsonProperty("expires_in")
    Long expiresIn,

    @JsonProperty("error")
    String error,

    @JsonProperty("error_description")
    String errorDescription

) {

    public boolean isError() {
        return error != null && !error.isEmpty();
    }
}