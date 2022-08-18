package com.example.hellpyending.article.domain;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @Column(name = "create_at")
    private LocalDateTime create;

    @Column(name = "update_at")
    private LocalDateTime update;

    @Column(name = "delete_yn")
    private char deleteYn;

    @Column(name = "hashtag_name")
    private String hashtagName;

}
