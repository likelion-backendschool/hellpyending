package com.example.hellpyending.user;

import com.example.hellpyending.config.UsersContext;
import com.example.hellpyending.config.Util;
import com.example.hellpyending.user.entity.UserCreateForm;
import com.example.hellpyending.user.entity.UserOauth2CreateForm;
import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    String login(@RequestParam(value = "error", required = false)String error,
                 @RequestParam(value = "exception", required = false)String exception,
                 UserCreateForm userCreateForm, Model model){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
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
    String information_update(Model model, Principal principal,UserUpdateForm userUpdateForm, Authentication authentication,
                              @AuthenticationPrincipal UserDetails userDetails){
        Users users = userService.getUser(principal.getName());

        System.out.println("authentication: " + authentication.getPrincipal());
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

    @GetMapping("/oauth2/information/update")
    @PreAuthorize("isAuthenticated()")
    String oauth2_information_update(Model model, UserOauth2CreateForm userOauth2CreateForm, Authentication authentication,
                                     @AuthenticationPrincipal UsersContext usersContext){
        String email = ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttribute("email");
        Users users = userService.findByEmail(email);
        if (firstLoginCheck(users)){
            return "/user/oauth2_signup";
        }
        return "redirect:/";
    }

    @PostMapping("/oauth2/information/update")
    @PreAuthorize("isAuthenticated()")
    String oauth2_information_update(@Valid UserOauth2CreateForm userOauth2CreateForm, BindingResult bindingResult, Authentication authentication,
                                     @AuthenticationPrincipal UserDetails userDetails){
        String email = ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttribute("email");
        String birth = "%s-%s-%s".formatted(userOauth2CreateForm.getYear(),userOauth2CreateForm.getMonth(),userOauth2CreateForm.getDay());
        LocalDate birthday = LocalDate.parse(birth, DateTimeFormatter.ISO_DATE);
        if (bindingResult.hasErrors()) {
            return "/user/oauth2_signup";
        }
        try {
            userService.create(
                    email,
                    birthday,
                    userOauth2CreateForm.getSex(),
                    userOauth2CreateForm.getPhoneNumber(),
                    userOauth2CreateForm.getAddress_1st(),
                    userOauth2CreateForm.getAddress_2st(),
                    userOauth2CreateForm.getAddress_3st(),
                    userOauth2CreateForm.getAddress_4st(),
                    userOauth2CreateForm.getAddress_detail()
            );
        }
        catch (DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "/user/oauth2_signup";
        }
        catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "/user/oauth2_signup";
        }
        return "redirect:/";
    }
    @GetMapping("/password")
    public String pwdFind() {
        return "/user/password";
    }


    @PostMapping("/password")
    public String pwdChange(String email,String username,String password2) {
        Optional<Users> users_= userService.findByEmailAndUsername(email,username);
        if (!users_.isPresent()){
            return "access_error";
        }
        Users users = users_.get();
        userService.modifyPwd(users,password2);
        return "redirect:/";
    }

    private boolean firstLoginCheck(Users users) {
        return users.getAddress_1st() == null || users.getAddress_2st() == null || users.getAddress_3st() == null || users.getAddress_4st() == null ||
                users.getPhoneNumber() == null || users.getBirthday() == null;
    }

}
