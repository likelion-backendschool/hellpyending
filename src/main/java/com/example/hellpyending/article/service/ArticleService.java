package com.example.hellpyending.article.service;


import com.example.hellpyending.article.domain.Article;
import com.example.hellpyending.article.domain.ArticleComment;
import com.example.hellpyending.article.repository.ArticleCommentRepository;
import com.example.hellpyending.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public List<Article> findAll() {

//        List<Article> articleList = articleRepository.findAll();
//
//        articleList.forEach(article ->
//                article.getArticleCommentList()
//                        .addAll(articleCommentRepository.findByArticleAndCommentBundle(article, null))
//        );

        List<Article> articleList = articleRepository.findAll();
        List<ArticleComment> articleCommentList = articleCommentRepository.findAll();



        articleList.forEach(article ->
                article.getArticleCommentList().addAll(articleCommentList.stream().filter(articleComment ->
                        articleComment.getArticle().getId().equals(article.getId()) && articleComment.getCommentBundle() == null
                ).toList())
        ); // 첫번째 성능이 제곱으로 느려짐 댓글마다 필터링을 해야함

        return articleList;

    }

}
