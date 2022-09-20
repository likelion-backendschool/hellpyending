package com.example.hellpyending.payment.controller;


import com.example.hellpyending.payment.dto.PostPaymentReq;
import com.example.hellpyending.payment.service.paymentService;
import com.example.hellpyending.user.entity.Users;
import com.example.hellpyending.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/payment")
@Log4j2
public class paymentController {
    private final paymentService paymentService;
    private final UserService userService;
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/create")
    public String create(@RequestBody PostPaymentReq postPaymentReq1) {
        Users user = userService.getUser(postPaymentReq1.getBuyer_name());
        paymentService.create(postPaymentReq1,user);
        return "redirect:/";
    }

//    //채팅방 개설
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping(value = "/create")
//    public String create1(@RequestBody PostPaymentReq postPaymentReq2){
//
//        paymentService.create(postPaymentReq2);
//        return "/";
//    }
//}
}
