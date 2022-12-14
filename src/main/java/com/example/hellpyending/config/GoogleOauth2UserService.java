package com.example.hellpyending.config;

import com.example.hellpyending.src.user.service.UserService;
import com.example.hellpyending.src.user.entity.UserType;
import com.example.hellpyending.src.user.entity.Users;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
// 구글은 OpenID Connect 1.0 를 이용하여 통신하기 때문에 OidcUserRequest 라고 쓰는 것을 유의하자.
public class GoogleOauth2UserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    private final UserService userService;

    // TODO : 나중에 꼭 수정해야한다.
    // 이 부분은 순환참조 때문에 매우 위험한 코드이다.
    // 잠깐 @Lazy 로 지연 해결 프록시를 이용하여 순환참조를 방지하고 있다.
    GoogleOauth2UserService(@Lazy UserService userService){
        this.userService = userService;
    }
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        final OidcUserService oidcUserService = new OidcUserService();

        // 유저 정보 조회를 하여 객체를 반환
        final OidcUser oidcUser = oidcUserService.loadUser(userRequest);
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();
        final OAuth2AccessToken accessToken = userRequest.getAccessToken();

        // 기본적으로 스프링 부트에서 OAuth2 회원 정보를 가져오기 위한 getAttributes 메서드가 있다.
        final String username = "GOOGLE_%s".formatted(oidcUser.getName());
        String email = oidcUser.getAttributes().get("email").toString();
        // name과 email을 가져와 회원가입을 시키게 만듦.
        userService.requestRegistration(username,email);

        Optional<Users> users_2 = userService.findByEmail(email);
        Users users = Util.userContextSave(users_2,username,email);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(UserType.USER.getUserType()));
        return (OidcUser) new UsersContext(users,authorities,oidcUser.getAttributes(),userNameAttributeName);
    }


}
