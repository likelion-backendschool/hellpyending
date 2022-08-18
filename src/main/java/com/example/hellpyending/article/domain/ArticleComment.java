package com.example.hellpyending.article.domain;

import com.example.hellpyending.src.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_comment_id")
    private Long id;

    @Column(name = "create_at")
    private LocalDateTime create;

    @Column(name = "update_at")
    private LocalDateTime update;

    @Column(name = "delete_yn")
    @JsonIgnore
    private char deleteYn;

    @Column(name = "comment_depth")
    private int commentDepth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_bundle", referencedColumnName ="board_comment_id")
//    @JsonIgnoreProperties({"commentBundle", "article", "child"})
    @JsonIgnore
    private ArticleComment commentBundle;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Article article;

    @OneToMany(mappedBy = "commentBundle")
    @JsonIgnoreProperties({"commentBundle", "article", "child"})
    private List<ArticleComment> child;
}