package com.example.hellpyending.article.domain;

import lombok.*;

import javax.persistence.*;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ArticleHashtag {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "hashtag_name")
    private String hashtagName;

}