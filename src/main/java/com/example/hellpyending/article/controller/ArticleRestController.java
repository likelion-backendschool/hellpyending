package com.example.hellpyending.article.controller;

import com.example.hellpyending.article.domain.Article;
import com.example.hellpyending.article.domain.ArticleComment;
import com.example.hellpyending.article.service.ArticleCommentService;
import com.example.hellpyending.article.service.ArticleService;
import com.example.hellpyending.user.UserService;
import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Optional;

@RequestMapping("/articleComment")
@Controller
@RequiredArgsConstructor
public class ArticleRestController {
    private final ArticleCommentService articleCommentService;
    @GetMapping("/CommentReply/save")
    @ResponseBody
    public String check(String SaveCommentReply,Long CommentId){

        ArticleComment articleComment = articleCommentService.getArticleComment(CommentId);


        Long articleCommentId = articleCommentService.modifyReply(articleComment, SaveCommentReply);
        return SaveCommentReply;
    }
}
