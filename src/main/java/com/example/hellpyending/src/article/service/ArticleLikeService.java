package com.example.hellpyending.src.article.service;

import com.example.hellpyending.src.article.domain.ArticleLike;
import com.example.hellpyending.src.article.repository.ArticleLikeRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleLikeService {
    private final ArticleLikeRepository articleLikeRepository;

    public int countArticleLike(Long articleId) {
        return articleLikeRepository.countByArticleId(articleId);
    }

    public boolean isArticleLike(Long usersId, Long articleId) {
        int result = articleLikeRepository.countByUsersIdAndArticleId(usersId, articleId);
        return result != 0;
    }

    public void doLike(ArticleLike articleLike) {
        articleLikeRepository.save(articleLike);
    }

    @Transactional
    public void undoLike(Long usersId, Long articleId) {
        Optional<ArticleLike> findArticleLike = articleLikeRepository.findByUsersIdAndArticleId(usersId, articleId);
        findArticleLike.ifPresent(articleLikeRepository::delete);
    }

}
