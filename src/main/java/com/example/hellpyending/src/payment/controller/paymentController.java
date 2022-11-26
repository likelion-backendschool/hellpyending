package com.example.hellpyending.src.payment.controller;


import com.example.hellpyending.src.payment.dto.PostPaymentReq;
import com.example.hellpyending.src.payment.service.paymentService;
import com.example.hellpyending.src.user.entity.Users;
import com.example.hellpyending.src.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/payment")
@Log4j2
public class paymentController {
    private final paymentService paymentService;
    private final UserService userService;
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/create")
    public String create(@RequestBody PostPaymentReq postPaymentReq1, Principal principal) {
        if (principal == null) {
            return "access_error";
        }
        Optional<Users> user = userService.findByNickname(postPaymentReq1.getBuyer_name());
        paymentService.create(postPaymentReq1,user);
        return "index";
    }

}
