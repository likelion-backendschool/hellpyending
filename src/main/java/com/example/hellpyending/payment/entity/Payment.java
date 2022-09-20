package com.example.hellpyending.payment.entity;


import com.example.hellpyending.user.entity.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imp_uid;
    private String merchant_uid;
    private String buyer_email;
    private String buyer_name;
    private String buyer_tel;
    private int amount;
    private String pay_method;
    private String gym_product;

    @Column(name = "create_at")
    private LocalDateTime create;

    @Column(name = "update_at")
    private LocalDateTime update;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

}
