package com.example.hellpyending.article.controller;

import com.example.hellpyending.article.domain.Article;
import com.example.hellpyending.article.domain.ArticleComment;
import com.example.hellpyending.article.form.ArticleCommentForm;
import com.example.hellpyending.article.service.ArticleCommentService;
import com.example.hellpyending.article.service.ArticleService;
import com.example.hellpyending.user.service.UserService;
import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/articleComment")
@Controller
@RequiredArgsConstructor
public class ArticleCommentController {
    private final ArticleService articleService;
    private final ArticleCommentService articleCommentService;

    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String detail(Principal principal, Model model, @PathVariable long id, @Valid ArticleCommentForm articleCommentForm, BindingResult bindingResult) {
        Article article = this.articleService.getArticle(id);

        if ( bindingResult.hasErrors() ) {
            model.addAttribute("article", article);
            return "article_detail";
        }

        Users users = userService.getUser(principal.getName());

        articleCommentService.create(article, articleCommentForm.getContent(),users);

        return "redirect:/article/detail/%d".formatted(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String articleCommentDelete(Principal principal, @PathVariable("id") long id) {
        ArticleComment articleComment = this.articleCommentService.getArticleComment(id);

        if (!articleComment.getUsers().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        if (articleComment.getCommentDepth() == 1) {
            Long articleCommentId = articleCommentService.deleteReply(articleComment);
            ArticleComment articleComment1 = articleCommentService.getArticleComment(articleCommentId);
            return String.format("redirect:/article/detail/%s", articleComment1.getArticle().getId());
        } else {
            this.articleCommentService.delete(articleComment);

            return String.format("redirect:/article/detail/%s", articleComment.getArticle().getId());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String articleModify(ArticleCommentForm articleCommentForm, @PathVariable("id") Long id, Principal principal) {
        ArticleComment articleComment = articleCommentService.getArticleComment(id);

        if (!articleComment.getUsers().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        articleCommentForm.setContent(articleComment.getComment());

        return "articleComment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String articleCommentModify(@Valid ArticleCommentForm articleCommentForm, BindingResult bindingResult, @PathVariable("id") Long id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "article_detail";
        }

        ArticleComment articleComment = articleCommentService.getArticleComment(id);

        if (!articleComment.getUsers().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        if (articleComment.getCommentDepth() == 1) {
            Long articleCommentId = articleCommentService.modifyReply(articleComment, articleCommentForm.getContent());
            ArticleComment articleComment1 = articleCommentService.getArticleComment(articleCommentId); // 게시글 아이디를 가져오기 위함
            return "redirect:/article/detail/%d".formatted(articleComment1.getArticle().getId()); // 대댓글의 수정
        } else {
            articleCommentService.modify(articleComment, articleCommentForm.getContent());

            return "redirect:/article/detail/%d".formatted(articleComment.getArticle().getId(), articleComment.getId()); // 댓글의 수정
        }

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/createReply/{id}")
    public String articleCommentReply(Model model, @Valid ArticleCommentForm articleCommentForm, BindingResult bindingResult, @PathVariable("id") Long id, Principal principal) {
//        ArticleComment articleComment = articleCommentService.getArticleComment(id);

//        if ( bindingResult.hasErrors() ) {
//            model.addAttribute("articleComment", articleComment);
//            return "article_detail";
//        }

//        Users users = userService.getUser(principal.getName());

        if(principal == null){
            // null 처리
        }

            ArticleComment articleComment = articleCommentService.createReply(id, articleCommentForm.getContent(), principal.getName());

            // null 처리

            return "redirect:/article/detail/%d".formatted(articleComment.getArticle().getId());
    }

//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/modify/{id}")
//    public String articleCommentReplyModify(@Valid ArticleCommentForm articleCommentForm, BindingResult bindingResult, @PathVariable("id") Long id, Principal principal) {
//        if (bindingResult.hasErrors()) {
//            return "article_detail";
//        }
//
//        ArticleComment articleCommentReply = articleCommentService.getArticleComment(id);
//
//        if (!articleCommentReply.getUsers().getUsername().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//        }
//
//        Long articleCommentId = articleCommentService.modifyReply(articleCommentReply, articleCommentForm.getContent());
//
//        ArticleComment articleComment = articleCommentService.getArticleComment(articleCommentId);
//
//        return "redirect:/article/detail/%d".formatted(articleComment.getArticle().getId());
//    }

}
