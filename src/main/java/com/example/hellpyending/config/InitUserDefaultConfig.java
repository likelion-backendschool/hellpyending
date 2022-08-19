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
        Users user = new Users();
        user.setName("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setSex(Sex.MALE);
        user.setEmail("admin@admin.com");
        user.setPhoneNumber("01012341234");
        user.setBirthday(LocalDate.now());
        user.setAddress_1st("어드민");
        user.setAddress_2st("어드민");
        user.setAddress_3st("어드민");
        user.setAddress_4st("어드민");
        user.setAddress_detail("어드민");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUserType(UserType.ADMIN);
        userService.create(user);
    }
}
