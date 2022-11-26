package com.example.hellpyending.src.payment.repository;

import com.example.hellpyending.src.payment.entity.Payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface paymentRepository extends JpaRepository<Payment, Long> {
}
