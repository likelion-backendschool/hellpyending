package com.example.hellpyending.payment.repository;

import com.example.hellpyending.payment.entity.Payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface paymentRepository extends JpaRepository<Payment, Long> {
}
