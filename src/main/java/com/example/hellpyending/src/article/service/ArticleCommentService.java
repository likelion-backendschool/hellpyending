package com.example.hellpyending.src.article.service;

import com.example.hellpyending.src.article.domain.Article;
import com.example.hellpyending.src.article.domain.ArticleComment;
import com.example.hellpyending.src.article.exception.DataNotFoundException;
import com.example.hellpyending.src.article.repository.ArticleCommentRepository;
import com.example.hellpyending.src.user.entity.DeleteType;
import com.example.hellpyending.src.user.repository.UserRepository;
import com.example.hellpyending.src.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleCommentService {
    private final ArticleCommentRepository articleCommentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(Article article, String content, Users users) {
        ArticleComment articleComment = new ArticleComment();
        articleComment.setComment(content);
        articleComment.setDeleteYn(DeleteType.NORMAL);
        articleComment.setCreate(LocalDateTime.now());
        article.addArticleComment(articleComment);
        articleComment.setUsers(users);
        articleCommentRepository.save(articleComment);
    }

    @Transactional
    public ArticleComment getArticleComment(Long id) {
        Optional<ArticleComment> answer = this.articleCommentRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    @Transactional
    public void delete(ArticleComment articleComment) {
        this.articleCommentRepository.delete(articleComment);
    }

    @Transactional
    public void modify(ArticleComment articleComment, String content) {
        articleComment.setComment(content);
        articleComment.setUpdate(LocalDateTime.now());
        articleCommentRepository.save(articleComment);
    }

    @Transactional
    public ArticleComment createReply(Long id, String content, String userName) {

        Optional<ArticleComment> articleCommentOptional = articleCommentRepository.findById(id);
        Optional<Users> usersOptional = userRepository.findByUsername(userName);

        if (articleCommentOptional.isEmpty()){
            return null;
        } else if (usersOptional.isEmpty()){
            return null;
        }

        ArticleComment articleComment = articleCommentOptional.get();
        Users users = usersOptional.get();

        ArticleComment articleComment1 = new ArticleComment();
        articleComment1.setComment(content);
        articleComment1.setDeleteYn(DeleteType.NORMAL);
        articleComment1.setCreate(LocalDateTime.now());
        articleComment1.setCommentDepth(1);
        articleComment1.setUsers(users);

        articleComment.addChild(articleComment1);
//        articleComment1.setCommentBundle(articleComment);
//        articleComment.getChild().add(articleComment1);
        return articleComment;
    }

    @Transactional
    public Long modifyReply(ArticleComment articleComment, String content) {
        Optional<ArticleComment> articleCommentReply = articleCommentRepository.findById(articleComment.getId());

        articleComment.setComment(content);
        articleComment.setUpdate(LocalDateTime.now());
        articleCommentRepository.save(articleComment);

        return articleCommentReply.get().getCommentBundle().getId();
    }

    @Transactional
    public Long deleteReply(ArticleComment articleComment) {
        Optional<ArticleComment> articleCommentReply = articleCommentRepository.findById(articleComment.getId());

        this.articleCommentRepository.delete(articleComment);

        return articleCommentReply.get().getCommentBundle().getId();
    }
}
