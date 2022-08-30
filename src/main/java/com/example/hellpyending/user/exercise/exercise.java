package com.example.hellpyending.user.exercise;

import com.example.hellpyending.config.BaseTimeEntity;
import com.example.hellpyending.user.entity.Users;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class exercise extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200) // varchar(200)
    private String Type;

    @Enumerated(EnumType.STRING)
    @Column
    private ExerciseIntensity Intensity;

    @Column
    private Integer hour;

    @Column
    private Integer calorie;

    @ManyToOne
    private Users author;

}
