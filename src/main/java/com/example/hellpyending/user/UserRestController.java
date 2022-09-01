package com.example.hellpyending.user;

import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    @GetMapping("/information/check")
    @ResponseBody
    public String check(Principal principal, String nickname){
        Optional<Users> users_ = userService.findByNickname(nickname);
        if (!users_.isPresent()){
            return "사용 가능한 아이디입니다.";
        }

        return "닉네임이 중복 되었습니다.";
    }
}
