package com.example.hellpyending.article.domain;

import com.example.hellpyending.user.entity.DeleteType;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "delete_yn")
    private DeleteType deleteYn;

    @Column(name = "img_url")
    private String imageUrl;

    @Column(name = "img_origin_name")
    private String imageOriginName;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Article article;
}

