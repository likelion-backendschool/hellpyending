package com.example.hellpyending.article.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleToHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_to_hashtag")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    private ArticleHashtag hashtag;
}

