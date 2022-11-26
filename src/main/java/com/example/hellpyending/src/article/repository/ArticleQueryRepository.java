package com.example.hellpyending.src.article.repository;

import com.example.hellpyending.src.article.domain.Article;
import com.example.hellpyending.src.user.entity.DeleteType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface
ArticleQueryRepository {

    Page<Article> findByUserContainsAndDeleteYn(String kw, DeleteType deleteYn, Pageable pageable);
    Page<Article> findByHashTagContainsAndDeleteYn(String kw, DeleteType deleteYn, Pageable pageable);

}
