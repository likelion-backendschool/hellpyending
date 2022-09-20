package com.example.hellpyending.payment.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PostPaymentReq {
    private String imp_uid;
    private String merchant_uid;
    private String buyer_email;
    private String buyer_name;
    private String buyer_tel;
    private int amount;
    private String pay_method;
}
