package com.example.hellpyending.src.article.repository;

import com.example.hellpyending.src.article.domain.ArticleComment;
import com.example.hellpyending.src.user.entity.DeleteType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

    List<ArticleComment> findByUsers_IdAndDeleteYn(Long id, DeleteType normal);
}
