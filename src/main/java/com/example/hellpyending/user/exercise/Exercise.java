package com.example.hellpyending.user.exercise;

import com.example.hellpyending.config.BaseTimeEntity;
import com.example.hellpyending.user.entity.Users;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exercise extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime DayOfWeek;

    @Column
    private DayType day;

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
