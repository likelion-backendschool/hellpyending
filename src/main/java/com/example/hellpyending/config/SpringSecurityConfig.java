package com.example.hellpyending.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity(debug = true) // WebSecurity에서 어떤 필터를 거쳤는지 알 수 있음.
@AllArgsConstructor
public class SpringSecurityConfig {
    private final Environment environment; // 각 환경에 대한 설정 변경이 필요하기 위해 가져옴.
    private final String registration = "spring.security.oauth2.client.registration."; // facebook과 google의 커스텀 마이징을 위한 yml 파일 가져오기.

    private final FacebookOauth2UserService facebookOauth2UserService;
    private final GoogleOauth2UserService googleOauth2UserService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.httpBasic().disable(); // basic filter 비활성화
        http.rememberMe(); // 로그인 유지 기능 ( 브라우저 창을 닫더라도 세션 값이 유지가 되어 로그인이 되어있음 )
        http.csrf(); // csrf 가 기본 값으로 들어있지만 명시적으로 작성.
        http.authorizeRequests()
                // GET 요청으로 "/test" URL을 접속 했을 때 인증 받은 사람(로그인 되어 있는 사람)만 접근이 가능하다.
                .antMatchers(HttpMethod.GET,"/test").authenticated()
                // GET 요청으로 "/user/auth" URL을 접속 했을 때 권한이 ADMIN인 사람만 접근이 가능하다.
                .antMatchers(HttpMethod.GET,"/user/admin").hasRole("ADMIN")
                // GET 요청으로 "/user/auth" URL을 접속 했을 때 권한이 USER인 사람만 접근이 가능하다.
                .antMatchers(HttpMethod.GET,"/user/user").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/article/create").authenticated()
                .antMatchers(HttpMethod.GET,"/articleComment/*").authenticated()
                .antMatchers(HttpMethod.POST,"/articleComment/*").authenticated()
                .antMatchers(HttpMethod.GET,"/articleComment/*/*").authenticated()
                .antMatchers(HttpMethod.POST,"/articleComment/*/*").authenticated()
                .antMatchers(HttpMethod.GET,"/exercise/*").authenticated()
                .antMatchers(HttpMethod.GET,"/user/login").permitAll()
                .antMatchers(HttpMethod.GET,"/user/signup").permitAll()
                .antMatchers(HttpMethod.POST,"/user/signup").permitAll()
                .antMatchers(HttpMethod.GET,"/user/*").authenticated()
                // 그 외 요청은 인증 받은 사람만 가능하게 만듦.
                .anyRequest().permitAll();

        // login
        http.formLogin()
                // login 페이지가 어떤 것인지 설정.
                .loginPage("/user/login")
                // login 성공시 루트 페이지로 이동 ( alwayUse를 false로 입력 시 접속 하려던 URL로 바로 이동 )
                .defaultSuccessUrl("/",false)
                // login은 모두 접근 가능
                .permitAll();
        // oauth2 login
        http.oauth2Login(oauth2 -> oauth2
                        .clientRegistrationRepository(clientRegistrationRepository())
                        .authorizedClientService(auth2AuthorizedClientService())
                        .userInfoEndpoint( user -> user
                                .oidcUserService(googleOauth2UserService) // google 인증, OpenID Connect 1.0를 이용하여 통신(인증)하기 때문에 메서드 이름이 다르다.
                                .userService(facebookOauth2UserService) // facebook 인증, OAuth2 통신
                        )
                        .defaultSuccessUrl("/user/oauth2/information/update",false)
                );
                // login 성공시 루트 페이지로 이동 ( alwayUse를 false로 입력 시 접속 하려던 URL로 바로 이동 )

                // login은 모두 접근 가능

        // logout
        http.logout()
                // logout 요청 경로
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                // logout 성공 시 루트 페이지로 이동
                .logoutSuccessUrl("/")
                // 로그아웃 시 session 종료
                .invalidateHttpSession(true)
        ;
         // 사이트 내 컨텐츠들이 다른 사이트에 포함되지 않도록해 'clickjacking' 공격 방지
        return http.build();
    }

    // web.ignoring().antMatchers("/images/**", "/css/**"); // 아래 코드와 같은 코드입니다.
    // 정적 리소스 spring security 대상에서 제외
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        // ADMIN 권한은 USER 권한을 가진다. ( ADMIN 권한은 USER 권한보다 더 세다(?) )
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }

    /*
    ClientRegistration에 대한 정보는 Authorization Server에서 해당 클라이언트의 정보가 필요할 때 사용한다.
    -> 즉 접속할 Provider에 대한 정보를 구현하는 것이다.
    */
    private ClientRegistration googleClientRegistration(){
        // yml 파일에서 spring.security.oauth2.client.registration.google.client-id 내용을 가져옴.
        final String clientId = environment.getProperty(registration + "google.client-id");
        // yml 파일에서 spring.security.oauth2.client.registration.google.client-secret 내용을 가져옴.
        final String clientSecret = environment.getProperty(registration + "google.client-secret");

        return CommonOAuth2Provider
                .GOOGLE
                .getBuilder("google")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scope(
                        "email",
                        "profile",
                        "openid"
//                        "https://www.googleapis.com/auth/user.addresses.read",
//                        "https://www.googleapis.com/auth/user.birthday.read"
                )
                .build();
    }

    private ClientRegistration facebookClientRegistration(){
        // yml 파일에서 spring.security.oauth2.client.registration.facebook.client-id 내용을 가져옴.
        final String clientId = environment.getProperty(registration + "facebook.client-id");
        // yml 파일에서 spring.security.oauth2.client.registration.facebook.client-secret 내용을 가져옴.
        final String clientSecret = environment.getProperty(registration + "facebook.client-secret");

        return CommonOAuth2Provider
                .FACEBOOK
                .getBuilder("facebook")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scope(
                        "public_profile",
                        "email", // 이메일 수집
                        "user_birthday", // 생년월일 수집
                        "user_gender", // 성별 수집
                        "user_location" // 위치 수집
                )
                .userInfoUri("https://graph.facebook.com/me?fields=id,name,email,gender,birthday,location")
                .build();
    }
    // 실제 토큰과 OAuth 와 통신
    @Bean
    public OAuth2AuthorizedClientService auth2AuthorizedClientService(){
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(){
        // 각 facebook, google의 provider에 대한 정보를 list로 저장.
        final List<ClientRegistration> clientRegistrations = Arrays.asList(
                googleClientRegistration(),
                facebookClientRegistration()
        );
        // 각 provider를 ClientRegistrationRepository 에다가 저장.
        // ClientRegistrationRepository : ClientRegistration에 대한 정보를 저장하는 저장소.
        return new InMemoryClientRegistrationRepository(clientRegistrations);
    }


}
