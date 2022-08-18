package com.example.hellpyending.article.repository;

import com.example.hellpyending.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
