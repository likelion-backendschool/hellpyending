<<<<<<<< HEAD:src/main/java/com/example/hellpyending/article/repository/ArticleRepository.java
package com.example.hellpyending.article.repository;

import com.example.hellpyending.article.domain.Article;
========
package com.example.hellpyending.article;

>>>>>>>> dev:src/main/java/com/example/hellpyending/article/ArticleRepository.java
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
