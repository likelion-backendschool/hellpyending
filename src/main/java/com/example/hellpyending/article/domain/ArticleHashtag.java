package com.example.hellpyending.article.domain;

import com.example.hellpyending.DeleteType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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