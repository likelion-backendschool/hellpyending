package com.example.hellpyending.article.controller;

import com.example.hellpyending.article.domain.ArticleComment;
import com.example.hellpyending.article.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
