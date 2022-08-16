package com.example.hellpyending.article;

import com.example.hellpyending.domain.AuditingFields;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
})
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title;  // 제목

    @Setter
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;  // 본문

    @Setter
    private String hashtag;  // 해시태그

    // mappedBy 를 걸지 않으면 두 entity 이름을 합쳐서 테이블을 하나 만듬
    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private boolean deleted = Boolean.FALSE;

    @PrePersist
    void createdAt() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    void modifiedAt() {
        modifiedAt = LocalDateTime.now();
    }

    // public, protected no-arg constructor -> entity
    protected Article() {
    }

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // factory method
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // Pattern Matching for instanceof in Java 14
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // junpyo 브랜치 추가합니다..
}
