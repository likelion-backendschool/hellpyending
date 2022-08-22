package com.example.hellpyending.article.service;

import com.example.hellpyending.article.domain.Article;
import com.example.hellpyending.article.domain.ArticleComment;
import com.example.hellpyending.article.exception.DataNotFoundException;
import com.example.hellpyending.article.repository.ArticleCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleCommentService {
    private final ArticleCommentRepository articleCommentRepository;

    public void create(Article article, String content) {
        ArticleComment articleComment = new ArticleComment();
        articleComment.setComment(content);
        articleComment.setDeleteYn('N');
        articleComment.setCreate(LocalDateTime.now());
        article.addArticleComment(articleComment);
        articleCommentRepository.save(articleComment);
    }

    public ArticleComment getArticleComment(Long id) {
        Optional<ArticleComment> answer = this.articleCommentRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }


    public void delete(ArticleComment articleComment) {
        this.articleCommentRepository.delete(articleComment);
    }
}
