package com.example.hellpyending.src.user;

import com.example.hellpyending.src.user.entity.Sex;
import com.example.hellpyending.src.user.entity.User;
import com.example.hellpyending.src.user.entity.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void create(User user) {
        userRepository.save(user);
    }

    public void create(String username, String password, Sex sex, String email, String phoneNumber, LocalDate birthday, String address) {
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setSex(sex);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setBirthday(birthday);
        user.setAddress(address);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUserType(UserType.USER);
        userRepository.save(user);
    }
}
