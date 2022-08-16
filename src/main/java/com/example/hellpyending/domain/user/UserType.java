package com.example.hellpyending.domain.user;



public enum UserType {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");
    private String userType;
    UserType(String userType) {
        this.userType = userType;
    }
}
