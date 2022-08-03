package com.example.project_version_init.board.domain;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Table(name = "Board")
@Entity
public class UserBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updatedAt;

    private String title;

    @Column(columnDefinition = "TEXT")
    @NotNull
    private String content;

    private String hashtag;  // 어떻게 구현할지 안 정해짐

    private Long hitCount;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    private String areaName;

    @ColumnDefault("0")
    private Boolean deleteYn;
}
