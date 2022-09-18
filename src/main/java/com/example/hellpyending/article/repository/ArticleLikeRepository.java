package com.example.hellpyending.article.repository;

import com.example.hellpyending.article.domain.Article;
import com.example.hellpyending.article.domain.ArticleLike;
import com.example.hellpyending.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    int countByUsersIdAndArticleId(Long usersId, Long articleId);
    Optional<ArticleLike> findByUsersIdAndArticleId(Long usersId, Long articleId);
    int countByArticleId(Long articleId);

}
