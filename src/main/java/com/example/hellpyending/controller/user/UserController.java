package com.example.hellpyending.controller.user;

import com.example.hellpyending.domain.user.User;
import com.example.hellpyending.domain.user.UserCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
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

        userService.create(userCreateForm.getUsername(),
                userCreateForm.getPassword1(),
                userCreateForm.getSex(),
                userCreateForm.getEmail(),
                userCreateForm.getPhoneNumber(),
                localDate,
                userCreateForm.getAddress());
        return "redirect:/";
    }
}
