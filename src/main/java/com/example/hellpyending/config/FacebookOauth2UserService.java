package com.example.hellpyending.config;


import com.example.hellpyending.user.UserService;
import com.example.hellpyending.user.entity.Sex;
import com.example.hellpyending.user.entity.UserType;
import com.example.hellpyending.user.entity.Users;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FacebookOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserService userService;

    FacebookOauth2UserService(@Lazy UserService userService){
        this.userService = userService;
    }
    // API를 통신해서 회원 정보를 가져오는 곳
    // 즉 구글 로그인 했을 때 회원 정보를 가져오는 곳.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

        // 유저 정보 조회를 하여 객체를 반환
        final OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        System.out.println("oAuth2User.getAttributes()" + oAuth2User.getAttributes());
        // 기본적으로 스프링 부트에서 OAuth2 회원 정보를 가져오기 위한 getAttributes 메서드가 있다.

        final String username = "FACEBOOK_%s".formatted(oAuth2User.getName());
        final String nickname = attributes.get("name").toString();
        final String email = attributes.get("email").toString();
        final String gender = attributes.get("gender").toString();
        String birthday = attributes.get("birthday").toString();
        // name과 email을 가져와 회원가입을 시키게 만듦.
        userService.requestRegistration(nickname,email,gender,birthday);
        String[] bits = birthday.split("/");
        birthday = "%s-%s-%s".formatted(bits[2],bits[0],bits[1]);
        LocalDate birth = LocalDate.parse(birthday, DateTimeFormatter.ISO_DATE);

        Users users = Users.builder()
                .username(username)
                .password("")
                .nickname(nickname)
                .email(email)
                .sex(gender == "male" ? Sex.MALE : Sex.FEMALE)
                .birthday(birth)
                .build();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(UserType.USER.getUserType()));
        return new UsersContext(users,authorities,oAuth2User.getAttributes(),userNameAttributeName);
    }
}