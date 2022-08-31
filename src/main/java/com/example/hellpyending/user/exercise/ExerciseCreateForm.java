package com.example.hellpyending.user.exercise;

import com.example.hellpyending.user.entity.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class ExerciseCreateForm {

    @Column
    private String DayOfWeek;

    @Column
    private String day;

    @Column(length = 200) // varchar(200)
    private String Type;

    @Enumerated(EnumType.STRING)
    @Column
    private ExerciseIntensity Intensity;

    @Column
    private Integer hour;

    @Column
    private Integer calorie;

}
