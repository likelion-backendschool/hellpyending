package com.example.hellpyending.article.controller;

import com.example.hellpyending.article.domain.Article;
import com.example.hellpyending.article.domain.ArticleComment;
import com.example.hellpyending.article.form.ArticleCommentForm;
import com.example.hellpyending.article.service.ArticleCommentService;
import com.example.hellpyending.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/articleComment")
@Controller
@RequiredArgsConstructor
public class ArticleCommentController {
    private final ArticleService articleService;
    private final ArticleCommentService articleCommentService;

    @PostMapping("/create/{id}")
    public String detail(Model model, @PathVariable long id, @Valid ArticleCommentForm articleCommentForm, BindingResult bindingResult) {
        Article article = this.articleService.getArticle(id);

        if ( bindingResult.hasErrors() ) {
            model.addAttribute("article", article);
            return "article_detail";
        }

        articleCommentService.create(article, articleCommentForm.getContent());

        return "redirect:/article/detail/%d".formatted(id);
    }

    @GetMapping("/delete/{id}")
    public String articleCommentDelete(@PathVariable("id") long id) {
        ArticleComment articleComment = this.articleCommentService.getArticleComment(id);
        this.articleCommentService.delete(articleComment);
        return String.format("redirect:/article/detail/%s", articleComment.getArticle().getId());
    }
}
