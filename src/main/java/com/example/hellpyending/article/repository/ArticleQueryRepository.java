package com.example.hellpyending.article.repository;

import com.example.hellpyending.article.domain.Article;
import com.example.hellpyending.user.entity.DeleteType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface
ArticleQueryRepository {

    Page<Article> findByUserContainsAndDeleteYn(String kw, DeleteType deleteYn, Pageable pageable);
    Page<Article> findByHashTagContainsAndDeleteYn(String kw, DeleteType deleteYn, Pageable pageable);

}
