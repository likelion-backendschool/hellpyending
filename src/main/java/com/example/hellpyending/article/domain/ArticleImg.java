package com.example.hellpyending.article.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_img_id")
    private Long id;

    @Column(name = "create_id")
    private LocalDateTime create;

    @Column(name = "update_id")
    private LocalDateTime update;

    @Column(name = "delete_yn")
    private char deleteYn;

    @Column(name = "img_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Article article;
}

