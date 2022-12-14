package com.example.hellpyending.src.payment.service;

import com.example.hellpyending.src.payment.dto.PostPaymentReq;
import com.example.hellpyending.src.payment.entity.Payment;
import com.example.hellpyending.src.user.entity.Users;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class paymentService {
    private final com.example.hellpyending.src.payment.repository.paymentRepository paymentRepository;
    public void create(PostPaymentReq postPaymentReq , Optional<Users> user) {


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
        payment.setUsers(user.get());
        paymentRepository.save(payment);
    }
}
