package com.example.hellpyending.src.user.entity;


import lombok.Getter;

@Getter
public enum UserType {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");
    private String userType;
    private UserType(String userType) {
        this.userType = userType;
    }
}
