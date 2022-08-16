package com.example.hellpyending.src.user.entity;



public enum UserType {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");
    private String userType;
    UserType(String userType) {
        this.userType = userType;
    }
}
