package com.example.hellpyending.src.gym.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Gym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String gymName;

    @Column
    private String gymAddress;

    @Column
    private String gymPhoneNumber;

    @Column
    private int per1Month;
    @Column
    private int per3Months;
    @Column
    private int per6Months;
    @Column
    private int per12Months;
    @Column
    private int pt_price;

    @Column(nullable = true)
    private String address_1st;
    @Column(nullable = true)
    private String address_2st;
    @Column(nullable = true)
    private String address_3st;
    @Column
    private double lat;


    @Column
    private double lng;


    @Column
    private String gym_image_address;

    @ColumnDefault("0")
    private Boolean deleteYn;

    @CreatedDate
    @Column(nullable = true, updatable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = true, updatable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAt;
}