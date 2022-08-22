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

    public void create(String username, String password, Sex sex, String email, String phoneNumber, LocalDate birthday,
                       String address_1st, String address_2st, String address_3st, String address_4st, String address_detail) {
        Users user = new Users();
        user.setName(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setSex(sex);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setBirthday(birthday);
        user.setAddress_1st(address_1st);
        user.setAddress_2st(address_2st);
        user.setAddress_3st(address_3st);
        user.setAddress_4st(address_4st);
        user.setAddress_detail(address_detail);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUserType(UserType.USER);
        userRepository.save(user);
    }

    public Optional<Users> findByName(String username) {
        return userRepository.findByName(username);
    }

    public Users getUser(String name) {
        return this.userRepository.findByName(name).orElseThrow(() -> new DataNotFoundException("siteuser not found"));
    }

}
