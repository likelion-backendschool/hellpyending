package com.example.hellpyending.article.controller;

import com.example.hellpyending.article.domain.Article;

import com.example.hellpyending.article.domain.ArticleImg;

import com.example.hellpyending.article.exception.DataNotFoundException;

import com.example.hellpyending.article.form.ArticleCommentForm;
import com.example.hellpyending.article.form.ArticleForm;
import com.example.hellpyending.article.service.ArticleImgService;
import com.example.hellpyending.article.service.ArticleService;
import com.example.hellpyending.user.UserSecurityService;
import com.example.hellpyending.user.UserService;
import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RequestMapping("/article")
@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleImgService articleImgService;
    private final ArticleService articleService;
    private final UserService userService;
    private final UserSecurityService userSecurityService;

    @GetMapping("/list")
    public String list(String kw, Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Article> paging = articleService.getList(kw, page);

        model.addAttribute("paging", paging);
        return "article_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable long id, ArticleCommentForm articleCommentForm) {
        Article article = articleService.getArticle(id);

        articleService.setHitCount(article);


        model.addAttribute("article", article);

        return "article_detail";
    }


//    @GetMapping("/")
//    public String root() {
//        return "redirect:/article/list";
//    }

    //테스트용
//    @GetMapping("/test")
//    public @ResponseBody List<Article> bundle() {
//
//            return articleService.getList();
//    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String articleCreate(ArticleForm articleForm) {
        return "article_form";
    }



    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String articleCreate_img(Model model, @Valid ArticleForm articleForm, BindingResult bindingResult, Principal principal
    ,@RequestParam(value = "files", required = false) List<MultipartFile> files
    ,@RequestParam(value = "tag", required = false) List<String> tags) throws IOException {
        if (bindingResult.hasErrors()) {
            return "article_form";
        }


        Users users = this.userService.getUser(principal.getName());
        articleService.create(articleForm.getTitle(), articleForm.getContent(), users,files,tags);

        return "redirect:/article/list"; // 질문 저장 후 질문 목록으로 이동
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String articleModify(ArticleForm articleForm, @PathVariable(name = "id") Long id, Principal principal) {

        Article article = this.articleService.getArticle(id);

        if (article == null) {
            throw new DataNotFoundException("%d번 질문은 존재하지 않습니다.");
        }

        if (!article.getUsers().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        articleForm.setTitle(article.getTitle());
        articleForm.setContent(article.getContent());
        articleForm.setUpdate(article.getUpdate());
        // 게시글 수정시 게시글 주소 수정은 안되게 했습니다.
        return "articleUpdate_form";
    }

    @PostMapping("/modify")
    public String articleModify(Model model, @Valid ArticleForm articleForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "article_form";
        }

        Article article = this.articleService.getArticle(articleForm.getId());

        if (article == null) {
            throw new DataNotFoundException("%d번 질문은 존재하지 않습니다.");
        }

        if (!article.getUsers().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        articleService.modify(article, articleForm.getTitle(), articleForm.getContent());

        return String.format("redirect:/article/detail/%s", articleForm.getId());

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String articleDelete(Principal principal, @PathVariable(name = "id") Long id) {
        Article article = articleService.getArticle(id);

        if (!article.getUsers().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        boolean result = articleService.delete(id);
        if (result){
            return "redirect:/article/list";
        } else {
            // 삭제실패관련 페이지나 메시지 리턴
            return null;
        }
    }



}
