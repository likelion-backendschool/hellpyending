package com.example.hellpyending.article.domain;

import com.example.hellpyending.DeleteType;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "delete_yn")
    private DeleteType deleteYn;

    @Column(name = "hashtag_name")
    private String hashtagName;

}
