package com.example.hellpyending.src.user.service;

import com.example.hellpyending.src.article.domain.Article;
import com.example.hellpyending.src.article.domain.ArticleComment;
import com.example.hellpyending.src.article.domain.ArticleLike;
import com.example.hellpyending.src.article.exception.DataNotFoundException;
import com.example.hellpyending.src.article.repository.ArticleCommentRepository;
import com.example.hellpyending.src.article.repository.ArticleLikeRepository;
import com.example.hellpyending.src.article.repository.ArticleRepository;
import com.example.hellpyending.config.Util;
import com.example.hellpyending.src.user.entity.DeleteType;
import com.example.hellpyending.src.user.entity.UserType;
import com.example.hellpyending.src.user.repository.UserRepository;
import com.example.hellpyending.src.user.entity.Sex;
import com.example.hellpyending.src.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserFindService userFindService;
    private final UserRepository userRepository;

    private final ArticleRepository articleRepository;

    private final ArticleCommentRepository articleCommentRepository;
    private final PasswordEncoder passwordEncoder;

    private final ArticleLikeRepository articleLikeRepository;


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
            final String email
    ){
        Optional<Users> users_ = this.findByEmail(email);
        if(users_.isPresent() == false){
            Users user = Users.builder()
                    .username(username)
                    .password(passwordEncoder.encode(Util.getTempPassword()))
                    .nickname(username)
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

    public void create(String email, String username, String nickname,LocalDate birth, Sex Sex,String phoneNumber, String address_1st, String address_2st, String address_3st, String address_4st, String address_detail) {
        Optional<Users> users_ = userRepository.findByEmailOrUsernameOrNickname(email,username,nickname);
        Users users = users_.get();
        users.setNickname(nickname);
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

    public Optional<Users> findByEmailOrUsernameOrNickname(String email, String name, String nickName) {
        return userRepository.findByEmailOrUsernameOrNickname(email,name,nickName);
    }

    public Optional<Users> findByEmailOrUsername(String email, String username) {
        return userRepository.findByEmailOrUsername(email,username);
    }

    @Transactional
    public void delete(String username) {
        Optional<Users> users_ = userRepository.findByUsername(username);
        Users users = users_.get();
        List<Article> articles = articleRepository.findByUsers_IdAndDeleteYn(users.getId(), DeleteType.NORMAL);
        List<ArticleComment> articleComments = articleCommentRepository.findByUsers_IdAndDeleteYn(users.getId(),DeleteType.NORMAL);
        List<ArticleLike> articleLikes  = articleLikeRepository.findByUsers_Id(users.getId());

        users.setDeleteYn(DeleteType.DELETE);
        for (Article article : articles){
            article.setDeleteYn(DeleteType.DELETE);
        }
        for (ArticleComment articleComment : articleComments){
            articleComment.setDeleteYn(DeleteType.DELETE);
            articleComment.setComment("????????? ????????? ???????????????.");
        }
        articleLikeRepository.deleteAll(articleLikes);
        articleRepository.saveAll(articles);
        articleCommentRepository.saveAll(articleComments);
        userRepository.save(users);
    }
}
