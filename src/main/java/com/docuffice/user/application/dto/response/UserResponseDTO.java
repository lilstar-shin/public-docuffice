package com.docuffice.user.application.dto.response;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;

    private String name;

    private String phone;

    private String email;

    private Long profileImageId;

    private String status;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;
}
