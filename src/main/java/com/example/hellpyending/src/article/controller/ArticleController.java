package com.example.hellpyending.src.article.controller;

import com.example.hellpyending.src.article.domain.Article;

import com.example.hellpyending.src.article.domain.ArticleLike;
import com.example.hellpyending.src.article.exception.DataNotFoundException;

import com.example.hellpyending.src.article.form.ArticleCommentForm;
import com.example.hellpyending.src.article.form.ArticleForm;
import com.example.hellpyending.src.article.service.ArticleImgService;
import com.example.hellpyending.src.article.service.ArticleLikeService;
import com.example.hellpyending.src.article.service.ArticleService;
import com.example.hellpyending.src.user.service.UserSecurityService;
import com.example.hellpyending.src.user.service.UserService;
import com.example.hellpyending.src.user.entity.Users;
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


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private final ArticleLikeService articleLikeService;
    private final UserService userService;

    private final UserSecurityService userSecurityService;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "")String kw, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "search_subject")String type) {
        Page<Article> paging = articleService.getList(type,kw, page);
        model.addAttribute("paging", paging);
        model.addAttribute("type",type);
        model.addAttribute("kw",kw);
        return "article_list";
    }
    @GetMapping("/list_new")
    public String list_new(String kw, Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Article> paging = articleService.getList(kw, page);

        model.addAttribute("paging", paging);
        return "index_new";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list/user")
    public String list(Principal principal, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "") String sortCode) {
        if (principal==null) {
            return "access_error";
        }
        Users users = userService.getUser(principal.getName());


        Page<Article> paging = articleService.getList(users.getId() , page, sortCode);

        model.addAttribute("paging", paging);
        return "article_list";
    }


    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable long id, ArticleCommentForm articleCommentForm, HttpServletRequest request, HttpServletResponse response) {
        Article article = articleService.getArticle(id);
        articleService.setHitCount(id, request, response); // ????????? ??????
        int pushCount = articleLikeService.countArticleLike(id);
        model.addAttribute("pushCount",pushCount);
        model.addAttribute("article", article);
        return "article_detail";
    }
    @GetMapping(value = "/detail_new/{id}")
    public String detail_new(Model model, @PathVariable long id, ArticleCommentForm articleCommentForm, HttpServletRequest request, HttpServletResponse response) {
        Article article = articleService.getArticle(id);
        articleService.setHitCount(id, request, response); // ????????? ??????
        int pushCount = articleLikeService.countArticleLike(id);
        model.addAttribute("pushCount",pushCount);
        model.addAttribute("article", article);
        return "article_detail_new";
    }


//    @GetMapping("/")
//    public String root() {
//        return "redirect:/article/list";
//    }

    //????????????
//    @GetMapping("/test")
//    public @ResponseBody List<Article> bundle() {
//
//            return articleService.getList();
//    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String articleCreate(ArticleForm articleForm, Principal principal) {

        if (principal==null) {
            return "access_error";

        }
        return "article_form";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create_new")
    public String articleCreate_new(ArticleForm articleForm, Principal principal) {

        if (principal==null) {
            return "access_error";

        }
        return "article_form_new";
    }


    @GetMapping("/pushLike/{articleId}")
    @ResponseBody
    public String pushLike(@PathVariable Long articleId, Principal principal) {
        if (principal == null) {
            return "AccessDenied";
        }
        Users users = userService.getUser(principal.getName());
        Article article = articleService.getArticle(articleId);
        boolean articleLike = articleLikeService.isArticleLike(users.getId(), article.getId());
        if (!articleLike) {
            articleLikeService.doLike(new ArticleLike(article, users));
            return "pushLike";
        } else {
            articleLikeService.undoLike(users.getId(), article.getId());
            return "unPushLike";
        }
    }


    @GetMapping("/likeStatus/{articleId}")
    @ResponseBody
    public String likeStatus(@PathVariable Long articleId, Principal principal) {
        if (principal == null) {
            return "AccessDenied";
        }
        Users users = userService.getUser(principal.getName());
        Article article = articleService.getArticle(articleId);
        return String.valueOf(articleLikeService.isArticleLike(users.getId(), article.getId()));
    }





    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String articleCreate_img(Model model, @Valid ArticleForm articleForm, BindingResult bindingResult, Principal principal
            ,@RequestParam(value = "files", required = false) List<MultipartFile> files
            ,@RequestParam(value = "tag", required = false) List<String> tags) throws IOException {
        if (bindingResult.hasErrors()) {
            return "article_form";
        }
        if (principal == null) {
            return "access_error";
        }
        Users users = this.userService.getUser(principal.getName());
        articleService.create(articleForm.getTitle(), articleForm.getContent(), users,files,tags);
        return "redirect:/article/list"; // ?????? ?????? ??? ?????? ???????????? ??????
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String articleModify(ArticleForm articleForm, @PathVariable(name = "id") Long id, Principal principal) {

        Article article = this.articleService.getArticle(id);

        if (article == null) {
            throw new DataNotFoundException("%d??? ????????? ???????????? ????????????.");
        }

        if (!article.getUsers().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "??????????????? ????????????.");
        }

        articleForm.setTitle(article.getTitle());
        articleForm.setContent(article.getContent());
        articleForm.setUpdate(article.getUpdate());
        // ????????? ????????? ????????? ?????? ????????? ????????? ????????????.
        return "articleUpdate_form";
    }

    @PostMapping("/modify")
    public String articleModify(Model model, @Valid ArticleForm articleForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "article_form";
        }
        if (principal == null) {
            return "access_error";
        }
        Article article = this.articleService.getArticle(articleForm.getId());

        if (article == null) {
            throw new DataNotFoundException("%d??? ????????? ???????????? ????????????.");
        }

        if (!article.getUsers().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "??????????????? ????????????.");
        }

        articleService.modify(article, articleForm.getTitle(), articleForm.getContent());

        return String.format("redirect:/article/detail/%s", articleForm.getId());

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String articleDelete(Principal principal, @PathVariable(name = "id") Long id) {
        Article article = articleService.getArticle(id);

        if (!article.getUsers().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "??????????????? ????????????.");
        }
        boolean result = articleService.delete(id);
        if (result){
            return "redirect:/article/list";
        } else {
            // ?????????????????? ???????????? ????????? ??????
            return null;
        }
    }


}
