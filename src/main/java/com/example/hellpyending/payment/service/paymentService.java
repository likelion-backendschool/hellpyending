package com.example.hellpyending.payment.service;

import com.example.hellpyending.payment.dto.PostPaymentReq;
import com.example.hellpyending.payment.entity.Payment;
import com.example.hellpyending.payment.repository.paymentRepository;
import com.example.hellpyending.user.entity.Users;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class paymentService {
    private final paymentRepository paymentRepository;
    public void create(PostPaymentReq postPaymentReq , Users user) {


        Payment payment = new Payment();
        payment.setImp_uid(postPaymentReq.getImp_uid());
        payment.setMerchant_uid(postPaymentReq.getMerchant_uid());
        payment.setBuyer_email(postPaymentReq.getBuyer_email());
        payment.setBuyer_name(postPaymentReq.getBuyer_name());
        payment.setBuyer_tel(postPaymentReq.getBuyer_tel());
        payment.setAmount(postPaymentReq.getAmount());
        payment.setGym_product(postPaymentReq.getGym_product());
        payment.setCreate(LocalDateTime.now());
        payment.setUpdate(LocalDateTime.now());
        payment.setUsers(user);
        paymentRepository.save(payment);
    }
}
