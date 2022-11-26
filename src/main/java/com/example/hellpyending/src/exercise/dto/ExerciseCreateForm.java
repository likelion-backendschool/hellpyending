package com.example.hellpyending.src.exercise.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
public class ExerciseCreateForm {
    @Column
    private String year;
    @Column
    private String month;
    @Column
    private String day;


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
