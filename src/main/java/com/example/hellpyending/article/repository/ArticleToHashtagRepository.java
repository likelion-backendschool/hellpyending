package com.example.hellpyending.article.repository;

import com.example.hellpyending.article.domain.ArticleToHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleToHashtagRepository extends JpaRepository<ArticleToHashtag, Long> {
}
