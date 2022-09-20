package com.example.hellpyending.payment.controller;


import com.example.hellpyending.payment.dto.PostPaymentReq;
import com.example.hellpyending.payment.service.paymentService;
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/create")
    public String create(@RequestBody PostPaymentReq postPaymentReq1) {

        paymentService.create(postPaymentReq1);
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
