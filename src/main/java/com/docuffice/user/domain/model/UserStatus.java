package com.docuffice.user.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    DELETED("DELETED");

    private final String code;

    public static UserStatus fromString(String status) {
        for (UserStatus userStatus : UserStatus.values()) {
            if (userStatus.code.equalsIgnoreCase(status)) {
                return userStatus;
            }
        }
        throw new IllegalArgumentException("No constant with text " + status + " found");
    }
}
