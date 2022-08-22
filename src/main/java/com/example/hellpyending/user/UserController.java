package com.example.hellpyending.user;

import com.example.hellpyending.user.entity.UserCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.time.LocalDate;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/login")
    String login(UserCreateForm userCreateForm){
        return "user_login";
    }
    @GetMapping("/signup")
    String signUp(UserCreateForm userCreateForm){
        return "user_signup";
    }
    @PostMapping("/signup")
    String signUp(@Valid UserCreateForm userCreateForm, BindingResult bindingResult){
        LocalDate localDate = LocalDate.parse(userCreateForm.getBirthday());
        if (bindingResult.hasErrors()) {
            return "user_signup";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "user_signup";
        }
        try {
            userService.create(
                    userCreateForm.getUsername(),
                    userCreateForm.getPassword1(),
                    userCreateForm.getSex(),
                    userCreateForm.getEmail(),
                    userCreateForm.getPhoneNumber(),
                    localDate,
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
            return "user_signup";
        }
        catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "user_signup";
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
}
