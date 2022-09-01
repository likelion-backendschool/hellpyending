package com.example.hellpyending.user;

import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    @GetMapping("/information/check")
    @ResponseBody
    public Users check(Principal principal){
        Optional<Users> users_ = userService.findByUsername(principal.getName());
        Users user = users_.get();
        return user;
    }
}
