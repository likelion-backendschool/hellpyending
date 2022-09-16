package com.example.hellpyending.article.domain;

import com.example.hellpyending.user.entity.Users;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "article_like",
        uniqueConstraints={
                @UniqueConstraint(
                        name="article_like_unique_key",
                        columnNames={"article_board_id", "user_id"}
                )
        })
public class ArticleLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    public ArticleLike(Article article, Users users) {
        this.article = article;
        this.users = users;
    }
}
