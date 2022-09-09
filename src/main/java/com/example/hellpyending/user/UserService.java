package com.example.hellpyending.user;

import com.example.hellpyending.article.exception.DataNotFoundException;
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
            final String name,
            final String email,
            final String gender,
            final String birthday
            ){
        final boolean existsEmail = userFindService.existsByEmail(email);
        final boolean existsUsername = userFindService.existsByUsername(name);

        if(existsEmail == false && existsUsername == false){


            LocalDate birth = LocalDate.parse(parseLocalDate(birthday), DateTimeFormatter.ISO_DATE);
            Users user = Users.builder()
                    .username(name)
                    .password(passwordEncoder.encode("1234"))
                    .nickname(name)
                    .sex(gender == "male" ? Sex.MALE : Sex.FEMALE )
                    .email(email)
                    .phoneNumber("01099999999")
                    .birthday(birth)
                    .address_1st("페이스북_1지역")
                    .address_2st("페이스북_2지역")
                    .address_3st("페이스북_3지역")
                    .address_4st("페이스북_4지역")
                    .address_detail("페이스북_상세지역")
                    .userType(UserType.USER)
                    .build();
            userRepository.save(user);
        }
    }

    public void requestRegistration(
            final String name,
            final String email
    ){
        final boolean existsEmail = userFindService.existsByEmail(email);
        final boolean existsUsername = userFindService.existsByUsername(name);

        if(existsEmail == false && existsUsername == false){


//            LocalDate birth = LocalDate.parse(parseLocalDate(birthday), DateTimeFormatter.ISO_DATE);
            Users user = Users.builder()
                    .username(name)
                    .password(passwordEncoder.encode("1234"))
                    .nickname(name)
                    .sex(Sex.MALE)
//                    .sex(gender == "male" ? Sex.MALE : Sex.FEMALE )
                    .email(email)
//                    .phoneNumber("01099999999")
//                    .birthday(birth)
                    .address_1st("구글_1지역")
                    .address_2st("구글_2지역")
                    .address_3st("구글_3지역")
                    .address_4st("구글_4지역")
                    .address_detail("구글_상세지역")
                    .userType(UserType.USER)
                    .build();
            userRepository.save(user);
        }
    }

    private String parseLocalDate(String birthday) {
        String[] bits = birthday.split("/");
        return "%s-%s-%s".formatted(bits[2],bits[0],bits[1]);
    }
}
