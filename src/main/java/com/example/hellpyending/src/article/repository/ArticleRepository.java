package com.example.hellpyending.src.article.repository;

import com.example.hellpyending.src.article.domain.Article;
import com.example.hellpyending.src.user.entity.DeleteType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>,ArticleQueryRepository {

    Page<Article> findByDeleteYn(DeleteType deleteYn, Pageable pageable);

    Page<Article> findByTitleContainsAndDeleteYn(String kw, DeleteType deleteYn, Pageable pageable);

    Optional<Article> findByIdAndDeleteYn(Long id, DeleteType deleteYn);
    Page<Article> findByUsers_IdAndDeleteYn(Long id, DeleteType deleteYn, Pageable pageable);
    @Query(nativeQuery = true,value = "select last_insert_id();")
    int last_insert_id();

    @Modifying
    @Query("update Article a set a.hitCount = a.hitCount + 1 where a.id = :id")
    int updateView(@Param("id") Long id);


    List<Article> findByUsers_IdAndDeleteYn(Long id, DeleteType normal);
}
