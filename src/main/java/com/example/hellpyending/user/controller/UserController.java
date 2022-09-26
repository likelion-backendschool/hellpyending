package com.example.hellpyending.user.controller;

import com.example.hellpyending.config.UsersContext;
import com.example.hellpyending.user.service.UserService;
import com.example.hellpyending.user.dto.UserUpdateForm;
import com.example.hellpyending.user.entity.UserCreateForm;
import com.example.hellpyending.user.entity.UserOauth2CreateForm;
import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

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

    /**
      유저 로그인
     **/
    @GetMapping("/login")
    String login(@RequestParam(value = "error", required = false)String error,
                 @RequestParam(value = "exception", required = false)String exception,
                 UserCreateForm userCreateForm, Model model){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/user/login";
    }
    /**
     유저 회원가입 창
     **/
    @GetMapping("/signup")
    String signUp(UserCreateForm userCreateForm){
        return "/user/signup";
    }
    /**
     유저 회원가입
     **/
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
    /**
     유저 정보 창
     **/
    @GetMapping("/information")
    @PreAuthorize("isAuthenticated()")
    String information(Model model, Principal principal, UserUpdateForm userUpdateForm){
        Users users = userService.getUser(principal.getName());
        model.addAttribute("users",users);
        return "/user/information";
    }
    /**
     유저 정보 수정 창
     **/
    @GetMapping("/information/{id}")
    @PreAuthorize("isAuthenticated()")
    String information_update(Model model, @PathVariable long id, Principal principal,UserUpdateForm userUpdateForm, Authentication authentication,
                              @AuthenticationPrincipal UserDetails userDetails){
        Users users = userService.getUser(principal.getName());

        System.out.println("authentication: " + authentication.getPrincipal());
        model.addAttribute("users",users);
        return "/user/information_update";
    }
    /**
     유저 정보 수정
     **/
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/information/{id}")
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

    /**
     oauth2 유저 회원가입 창
     **/
    @GetMapping("/oauth2/information")
    @PreAuthorize("isAuthenticated()")
    String oauth2_information_update(Model model, UserOauth2CreateForm userOauth2CreateForm, Authentication authentication,
                                     @AuthenticationPrincipal UsersContext usersContext,HttpSession httpSession){
        String email = ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttribute("email");
        Optional<Users> users_ = userService.findByEmailOrUsernameOrNickname(email,usersContext.getUsername(),usersContext.getNickname());
        Users users = users_.get();
        if (firstLoginCheck(users)){
            httpSession.invalidate();
            model.addAttribute("users",users);
            return "/user/oauth2_signup";
        }
        return "redirect:/";
    }
    /**
     oauth2 유저 회원가입
     **/
    @PostMapping("/oauth2/information")
    @PreAuthorize("isAuthenticated()")
    String oauth2_information_update(@Valid UserOauth2CreateForm userOauth2CreateForm, BindingResult bindingResult, String email,String username, String nickname, Model model){

        String birth = "%s-%s-%s".formatted(userOauth2CreateForm.getYear(),userOauth2CreateForm.getMonth(),userOauth2CreateForm.getDay());
        LocalDate birthday = LocalDate.parse(birth, DateTimeFormatter.ISO_DATE);
        Optional<Users> users_ = userService.findByEmail(email);
        Users users = users_.get();
        model.addAttribute("users",users);
        if (bindingResult.hasErrors()) {
            return "/user/oauth2_signup";
        }
        try {
            userService.create(
                    email,
                    username,
                    userOauth2CreateForm.getNickname(),
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
    /**
     oauth2 유저 패스워드 찾기 창
     **/
    @GetMapping("/password")
    public String pwdFind() {
        return "/user/password";
    }

    /**
     oauth2 유저 패스워드 찾기
     **/
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
                users.getPhoneNumber() == null || users.getBirthday() == null || users.getNickname() == null;
    }
    @GetMapping("/payment/{id}")
    public String payment(@PathVariable Long id,Model model){
        Optional<Users> users_ = userService.findById(id);
        if (!users_.isPresent()){
            return "/access_error";
        }
        Users users = users_.get();
        model.addAttribute("users",users);
        return "/user/payment";
    }
    @Transactional
    @GetMapping("/delete")
    public String delete(@AuthenticationPrincipal UsersContext usersContext,HttpSession httpSession){
        userService.delete(usersContext.getUsername());
        httpSession.invalidate();
        return "redirect:/";
    }
}
