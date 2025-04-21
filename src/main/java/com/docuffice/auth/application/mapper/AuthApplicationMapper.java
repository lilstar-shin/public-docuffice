package com.docuffice.auth.application.mapper;

import com.docuffice.auth.application.dto.response.GoogleUserInfoResponseDTO;
import com.docuffice.auth.application.dto.response.UserLoginResponseDTO;
import com.docuffice.auth.domain.model.Authentication;
import com.docuffice.auth.domain.model.OAuth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface AuthApplicationMapper {

    /*
     * RequestDTO -> Model
     */

    /*
     * Model -> ResponseDTO
     */
    UserLoginResponseDTO authenticationToUserLoginResponseDTO(Authentication model);

    /*
     * ResponseDTO -> Model
     */
    @Mapping(target = "authenticationProvider", constant = "GOOGLE")
    @Mapping(source = "dto.id", target = "providerUserId")
    @Mapping(source = "dto.email", target = "providerUserEmail")
    @Mapping(source = "dto.name", target = "providerUserName")
    @Mapping(source = "providerId", target = "providerId")
    OAuth googleUserInfoResponseToOAuth(GoogleUserInfoResponseDTO dto,
        Long providerId);


}
