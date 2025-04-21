package com.docuffice.user.domain.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class User {

    private Long id;

    private String name;

    private String phone;

    private String email;

    private Long profileImageId;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

}
