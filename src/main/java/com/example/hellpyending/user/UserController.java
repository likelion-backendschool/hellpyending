package com.example.hellpyending.user;

import com.example.hellpyending.user.entity.UserCreateForm;
import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    String login(UserCreateForm userCreateForm){
        return "/user/login";
    }
    @GetMapping("/signup")
    String signUp(UserCreateForm userCreateForm){
        return "/user/signup";
    }
    @PostMapping("/signup")
    String signUp(@Valid UserCreateForm userCreateForm, BindingResult bindingResult){

        String birth = "%s-%s-%s".formatted(userCreateForm.getYear(),userCreateForm.getMonth(),userCreateForm.getDay());
        LocalDate birthday = LocalDate.parse(birth, DateTimeFormatter.ISO_DATE);

        if (bindingResult.hasErrors()) {
            return "/user/signup";
        }
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "/user/signup";
        }
        try {
            userService.create(
                    userCreateForm.getUsername(),
                    userCreateForm.getPassword1(),
                    userCreateForm.getNickname(),
                    userCreateForm.getSex(),
                    userCreateForm.getEmail(),
                    userCreateForm.getPhoneNumber(),
                    birthday,
                    userCreateForm.getAddress_1st(),
                    userCreateForm.getAddress_2st(),
                    userCreateForm.getAddress_3st(),
                    userCreateForm.getAddress_4st(),
                    userCreateForm.getAddress_detail()
                    );
        }
        catch (DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "/user/signup";
        }
        catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "/user/signup";
        }
        return "redirect:/";
    }
    //    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @RequestMapping("/user")
    @ResponseBody
    public Authentication user(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    //    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @RequestMapping("/admin")
    @ResponseBody
    public Authentication admin(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/information")
    @PreAuthorize("isAuthenticated()")
    String information(Model model, Principal principal,UserUpdateForm userUpdateForm){
        Users users = userService.getUser(principal.getName());
        model.addAttribute("users",users);
        return "/user/information";
    }

    @GetMapping("/information/update")
    @PreAuthorize("isAuthenticated()")
    String information_update(Model model, Principal principal,UserUpdateForm userUpdateForm){
        Users users = userService.getUser(principal.getName());
        model.addAttribute("users",users);
        return "/user/information_update";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/information/update")
    String information_update(Model model, Principal principal, @Valid UserUpdateForm UserUpdateForm, BindingResult bindingResult, HttpSession httpSession){
        Users users = userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("users",users);
            return "/user/information_update";
        }
        if (UserUpdateForm.getPassword1() != null && !passwordEncoder.matches(UserUpdateForm.getPassword1(),users.getPassword())){
            bindingResult.reject("password1", "현재 비밀번호가 일치하지 않습니다.");
            model.addAttribute("users",users);
            return "/user/information_update";
        }


        try {
            userService.update(
                    users,
                    UserUpdateForm.getPassword2(),
                    UserUpdateForm.getNickname(),
                    UserUpdateForm.getPhoneNumber(),
                    UserUpdateForm.getAddress_1st(),
                    UserUpdateForm.getAddress_2st(),
                    UserUpdateForm.getAddress_3st(),
                    UserUpdateForm.getAddress_4st(),
                    UserUpdateForm.getAddress_detail()
            );
            httpSession.invalidate();
        }
        catch (DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "/user/signup";
        }
        catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "/user/signup";
        }
        model.addAttribute("users",users);
        return "redirect:/";
    }

    // 토큰 발행 하는 곳.
    @GetMapping("/token")
    public OAuth2AuthenticationToken home(final  OAuth2AuthenticationToken token){
        return token;
    }
}
