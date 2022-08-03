package com.example.project_version_init.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {
    ADMIN("ROLE_ADMIN", "administrator"),
    USER_1("ROLE_USER_LEVEL1", "user"),
    USER_2("ROLE_USER_LEVEL2", "user"),
    USER_3("ROLE_USER_LEVEL3", "user"),
    ;

    private final String key;
    private final String title;
}
