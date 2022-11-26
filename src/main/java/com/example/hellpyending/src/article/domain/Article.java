package com.example.hellpyending.src.article.domain;

import com.example.hellpyending.src.user.entity.DeleteType;
import com.example.hellpyending.src.user.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime create;

    @Column(name = "update_at", nullable = false) //, nullable = false
    private LocalDateTime update;

    @Column(name = "title")
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "hit_count", columnDefinition = "Integer default 0")
    private Integer hitCount;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "delete_yn")
    @JsonIgnore
    private DeleteType deleteYn;

    @Column(nullable = false)
    private String address_1st;

    // 시군구
    // ex) 강화군, 서구, 중구, 미추홀구 ...
    @Column(nullable = false)
    private String address_2st;

    // 동읍면리
    // ex) 간석동, 신현동, 관청리, 화정동 ...
    @Column(nullable = false)
    private String address_3st;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    Set<ArticleLike> likes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    //    @Transient
    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"article", "commentBundle"})
    private List<ArticleComment> articleCommentList;

    public void addArticleComment(ArticleComment articleComment) {
        articleComment.setArticle(this);
        getArticleCommentList().add(articleComment);
    }

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"article"})
    private List<ArticleImg> articleImgList;


    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<ArticleHashtag> articleHashtags = new HashSet<>();

    public void addArticleHashtag(String keywordContent) {
        articleHashtags.add(new ArticleHashtag(keywordContent));
    }

    public Article(Long id, LocalDateTime create, LocalDateTime update, String title, String content, Integer hitCount, String imageUrl, DeleteType deleteYn, String address_1st, String address_2st, String address_3st, Users users) {
        this.id = id;
        this.create = create;
        this.update = update;
        this.title = title;
        this.content = content;
        this.hitCount = hitCount;
        this.imageUrl = imageUrl;
        this.deleteYn = deleteYn;
        this.address_1st = address_1st;
        this.address_2st = address_2st;
        this.address_3st = address_3st;
        this.users = users;
    }


//    @OneToMany(mappedBy = "article")
//    @JsonIgnoreProperties({"article"})
//    private List<ArticleImg> articleImgList;
//
//    @OneToMany(mappedBy = "article")
//    @JsonIgnoreProperties({"article"})
//    private List<ArticleHashtag> articleHashtagList;
}