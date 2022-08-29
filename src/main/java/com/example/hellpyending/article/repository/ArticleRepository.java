package com.example.hellpyending.article.repository;

import com.example.hellpyending.DeleteType;
import com.example.hellpyending.article.domain.Article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public interface
ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findByDeleteYn(DeleteType deleteYn, Pageable pageable);

    Optional<Article> findByIdAndDeleteYn(Long id, DeleteType deleteYn);

    @Query(nativeQuery = true,value = "select last_insert_id();")
    int last_insert_id();
}
