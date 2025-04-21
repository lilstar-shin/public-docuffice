package com.docuffice.auth.application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class GoogleCallbackRequestDTO {

    @NotEmpty
    private String code;

    @NotEmpty
    private String redirectUri;

    @JsonIgnore
    private String ip;

    @JsonIgnore
    private String userAgent;
}
