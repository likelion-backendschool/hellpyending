package com.example.hellpyending.src.user.entity;

import com.example.hellpyending.config.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate dates;

    @Column
    private String DayOfWeek;

    @Column(length = 200) // varchar(200)
    private String Type;

    @Column
    private String Intensity;

    @Column
    private Integer hour;

    @Column
    private Integer calorie;

    @ManyToOne
    private Users author;

}
