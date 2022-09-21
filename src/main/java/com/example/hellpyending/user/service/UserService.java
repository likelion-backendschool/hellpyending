package com.example.hellpyending.user.service;

import com.example.hellpyending.article.exception.DataNotFoundException;
import com.example.hellpyending.config.Util;
import com.example.hellpyending.user.repository.UserRepository;
import com.example.hellpyending.user.entity.Sex;
import com.example.hellpyending.user.entity.Users;
import com.example.hellpyending.user.entity.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserFindService userFindService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


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
    public Optional<Users> findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    public Users getUser(String name) {
        return this.userRepository.findByUsername(name).orElseThrow(() -> new DataNotFoundException("siteuser not found"));
    }

    public void update(Users users, String newpassowrd, String nickname, String phoneNumber, String address_1st, String address_2st, String address_3st, String address_4st, String address_detail) {
        if(newpassowrd != null) {
            users.setPassword(passwordEncoder.encode(newpassowrd));
        }
        if(nickname != null){
            users.setNickname(nickname);
        }
        if(phoneNumber != null) {
            users.setPhoneNumber(phoneNumber);
        }
        if(address_1st != null) {
            users.setAddress_1st(address_1st);
        }
        if(address_2st != null) {
            users.setAddress_2st(address_2st);
        }
        if(address_3st != null) {
            users.setAddress_3st(address_3st);
        }
        if(address_4st != null) {
            users.setAddress_4st(address_4st);
        }
        if(address_detail != null) {
            users.setAddress_detail(address_detail);
        }
        userRepository.save(users);
    }
    public void requestRegistration(
            final String username,
            final String nickname,
            final String email,
            final String gender,
            final String birthday){
        final boolean existsEmail = userFindService.existsByEmail(email);
        final boolean existsUsername = userFindService.existsByUsername(username);
        final boolean existsNickname = userFindService.existsByNickname(nickname);

        if(existsEmail == false && existsUsername == false && existsNickname == false){
            LocalDate birth = LocalDate.parse(parseLocalDate(birthday), DateTimeFormatter.ISO_DATE);
            Users user = Users.builder()
                    .username(username)
                    .nickname(nickname)
                    .sex(gender == "male" ? Sex.MALE : Sex.FEMALE )
                    .email(email)
                    .birthday(birth)
                    .userType(UserType.USER)
                    .build();
            userRepository.save(user);
        }
    }

    public void requestRegistration(
            final String username,
            final String nickname,
            final String email
    ){
        Optional<Users> users_ = this.findByEmail(email);
        final boolean existsUsername = userFindService.existsByUsername(username);
        final boolean existsNickname = userFindService.existsByNickname(nickname);

        if(users_.isPresent() == false && existsUsername == false && existsNickname == false){
            Users user = Users.builder()
                    .username(username)
                    .nickname(nickname)
                    .password(passwordEncoder.encode(Util.getTempPassword()))
                    .email(email)
                    .userType(UserType.USER)
                    .build();
            userRepository.save(user);
        }
    }

    private static String parseLocalDate(String birthday) {
        String[] bits = birthday.split("/");
        return "%s-%s-%s".formatted(bits[2],bits[0],bits[1]);
    }

    public void create(String email, LocalDate birth, Sex Sex,String phoneNumber, String address_1st, String address_2st, String address_3st, String address_4st, String address_detail) {
        Optional<Users> users_ = userRepository.findByEmail(email);
        Users users = users_.get();
        users.setSex(Sex);
        users.setPhoneNumber(phoneNumber);
        users.setBirthday(birth);
        users.setAddress_1st(address_1st);
        users.setAddress_2st(address_2st);
        users.setAddress_3st(address_3st);
        users.setAddress_4st(address_4st);
        users.setAddress_detail(address_detail);
        userRepository.save(users);
    }

    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<Users> findByEmailAndUsername(String email, String username) {
        return userRepository.findByEmailAndUsername(email,username);
    }


    public void modifyPwd(Users users, String pwd) {
        users.setPassword(passwordEncoder.encode(pwd));
        userRepository.save(users);
    }

    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }
}
