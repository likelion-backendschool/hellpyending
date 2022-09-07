//package com.example.hellpyending.config;
//
//import com.example.hellpyending.DeleteType;
//import com.example.hellpyending.user.UserService;
//import com.example.hellpyending.user.entity.Sex;
//import com.example.hellpyending.user.entity.UserType;
//import com.example.hellpyending.user.entity.Users;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.time.LocalDate;
//
//@Configuration
//@RequiredArgsConstructor
//public class InitUserDefaultConfig {
//    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;
//    @Bean
//    public void initializeDefaultAdmin() {
//        Users user1 = Users.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("admin"))
//                .nickname("어드민이에요")
//                .sex(Sex.MALE)
//                .email("admin@admin.com")
//                .phoneNumber("01012341234")
//                .deleteYn(DeleteType.NORMAL)
//                .birthday(LocalDate.now())
//                .address_1st("어드민_1지역")
//                .address_2st("어드민_2지역")
//                .address_3st("어드민_3지역")
//                .address_4st("어드민_4지역")
//                .address_detail("어드민_상세주소")
//                .userType(UserType.ADMIN)
//                .build();
//        userService.create(user1);
//
//    }
//}