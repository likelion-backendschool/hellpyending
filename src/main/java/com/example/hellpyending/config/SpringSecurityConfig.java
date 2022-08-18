package com.example.hellpyending.config;

import com.example.hellpyending.user.UserService;
import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity(debug = true) // WebSecurity에서 어떤 필터를 거쳤는지 알 수 있음.
@RequiredArgsConstructor
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.httpBasic().disable(); // basic filter 비활성화
        http.rememberMe(); // 로그인 유지 기능 ( 브라우저 창을 닫더라도 세션 값이 유지가 되어 로그인이 되어있음 )
        http.csrf(); // csrf 가 기본 값으로 들어있지만 명시적으로 작성.
        http.authorizeRequests()
                // GET 요청으로 "/test" URL을 접속 했을 때 인증 받은 사람(로그인 되어 있는 사람)만 접근이 가능하다.
                .antMatchers(HttpMethod.GET,"/test").authenticated()
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

        // logout
        http.logout()
                // logout 요청 경로
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // logout 성공 시 루트 페이지로 이동
                .logoutSuccessUrl("/");
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

}
