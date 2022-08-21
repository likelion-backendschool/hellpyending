package com.example.hellpyending.config;

import com.example.hellpyending.user.UserService;
import com.example.hellpyending.user.entity.Sex;
import com.example.hellpyending.user.entity.UserType;
import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class InitUserDefaultConfig {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Bean
    public void initializeDefaultAdmin() {
        Users user1 = Users.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .sex(Sex.MALE)
                .email("admin@admin.com")
                .phoneNumber("01012341234")
                .birthday(LocalDate.now())
                .address_1st("어드민")
                .address_2st("어드민")
                .address_3st("어드민")
                .address_4st("어드민")
                .address_detail("어드민")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .userType(UserType.ADMIN)
                .build();
        userService.create(user1);

        Users user2 = Users.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .sex(Sex.MALE)
                .email("user@user.com")
                .phoneNumber("01011111111")
                .birthday(LocalDate.now())
                .address_1st("유저")
                .address_2st("유저")
                .address_3st("유저")
                .address_4st("유저")
                .address_detail("유저")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .userType(UserType.USER)
                .build();
        userService.create(user2);
    }
}
