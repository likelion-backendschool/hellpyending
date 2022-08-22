package com.example.hellpyending.article.repository;

import com.example.hellpyending.article.domain.Article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findByDeleteYn(char deleteYn, Pageable pageable);

    Optional<Article> findByIdAndDeleteYn(Long id, char deleteYn);
}
