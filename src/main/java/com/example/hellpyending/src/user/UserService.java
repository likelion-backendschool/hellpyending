package com.example.hellpyending.src.user;

import com.example.hellpyending.src.user.entity.Sex;
import com.example.hellpyending.src.user.entity.User;
import com.example.hellpyending.src.user.entity.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void create(User user) {
        userRepository.save(user);
    }

    public void create(String username, String password, Sex sex, String email, String phoneNumber, LocalDate birthday,
                       String address_1st, String address_2st, String address_3st, String address_4st, String address_detail) {
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setSex(sex);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setBirthday(birthday);
        user.setAddress_1st(address_1st);
        user.setAddress_2st(address_2st);
        user.setAddress_3st(address_3st);
        user.setAddress_4st(address_4st);
        user.setDeleteYn(false);
        user.setAddress_detail(address_detail);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUserType(UserType.USER);
        userRepository.save(user);
    }
}
