package com.docuffice.auth.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleUserInfoResponseDTO(
    @JsonProperty("id")
    String id,

    @JsonProperty("email")
    String email,

    @JsonProperty("verified_email")
    boolean verifiedEmail,

    @JsonProperty("name")
    String name,

    @JsonProperty("given_name")
    String givenName,

    @JsonProperty("family_name")
    String familyName,

    @JsonProperty("picture")
    String picture,

    @JsonProperty("locale")
    String locale
) {

}
