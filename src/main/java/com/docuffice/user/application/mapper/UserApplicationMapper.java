package com.docuffice.user.application.mapper;

import com.docuffice.user.application.dto.request.CreateUserRequestDTO;
import com.docuffice.user.application.dto.response.UserResponseDTO;
import com.docuffice.user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface UserApplicationMapper {

    /*
     * RequestDTO -> Model
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", expression = "java(com.docuffice.user.domain.model.UserStatus.ACTIVE.getCode())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "deletedAt", ignore = true)
    User userRequestDTOToUserModel(CreateUserRequestDTO createUserRequestDTO);

    /**
     * Model -> ResponseDTO
     */
    UserResponseDTO userModelToUserResponseDTO(User user);
}
