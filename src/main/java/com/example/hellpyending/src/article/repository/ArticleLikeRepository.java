package com.example.hellpyending.src.article.repository;

import com.example.hellpyending.src.article.domain.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    int countByUsersIdAndArticleId(Long usersId, Long articleId);
    Optional<ArticleLike> findByUsersIdAndArticleId(Long usersId, Long articleId);
    int countByArticleId(Long articleId);

    List<ArticleLike> findByUsers_Id(Long id);
}
