package com.example.hellpyending.user;

import com.example.hellpyending.article.exception.DataNotFoundException;
import com.example.hellpyending.user.entity.Sex;
import com.example.hellpyending.user.entity.Users;
import com.example.hellpyending.user.entity.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public void create(Users user) {
        userRepository.save(user);
    }

    public void create(String username, String password, String nickname, Sex sex, String email, String phoneNumber, LocalDate birthday,
                       String address_1st, String address_2st, String address_3st, String address_4st, String address_detail) {
        Users user = Users.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .sex(sex)
                .email(email)
                .phoneNumber(phoneNumber)
                .birthday(birthday)
                .address_1st(address_1st)
                .address_2st(address_2st)
                .address_3st(address_3st)
                .address_4st(address_4st)
                .address_detail(address_detail)
                .userType(UserType.USER)
                .build();
        userRepository.save(user);
    }

    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Users getUser(String name) {
        return this.userRepository.findByUsername(name).orElseThrow(() -> new DataNotFoundException("siteuser not found"));
    }

}
