package com.example.hellpyending.user.exercise.dto;

import com.example.hellpyending.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
public class ExerciseCreateForm {
    @Column
    private String Dates;


    @Column
    private String DayOfWeek;


    @Column(length = 200) // varchar(200)
    private String Type;

    @Enumerated(EnumType.STRING)
    @Column
    private String Intensity;


    @Column
    private Integer hour;


    @Column
    private Integer calorie;

}
