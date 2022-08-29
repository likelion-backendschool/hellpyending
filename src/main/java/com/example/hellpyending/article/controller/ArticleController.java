package com.example.hellpyending.article.controller;

import com.example.hellpyending.article.domain.Article;
import com.example.hellpyending.article.domain.ArticleImg;
import com.example.hellpyending.article.form.ArticleCommentForm;
import com.example.hellpyending.article.form.ArticleForm;
import com.example.hellpyending.article.service.ArticleImgService;
import com.example.hellpyending.article.service.ArticleService;
import com.example.hellpyending.user.UserSecurityService;
import com.example.hellpyending.user.UserService;
import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public String list(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Article> paging = articleService.getList(page);

        model.addAttribute("paging", paging);
        return "article_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable long id, ArticleCommentForm articleCommentForm) {
        Article article = articleService.getArticle(id);
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
        return "article_form_img";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String articleCreate(Model model, @Valid ArticleForm articleForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "article_form";
        }
        Users users = this.userService.getUser(principal.getName());
        articleService.create(articleForm.getTitle(), articleForm.getContent(), users.getAddress_1st());
        return "redirect:/article/list"; // 질문 저장 후 질문 목록으로 이동
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create_img")
    public String articleCreate_img(Model model, @Valid ArticleForm articleForm, BindingResult bindingResult, Principal principal
    ,@RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {
        if (bindingResult.hasErrors()) {
            return "article_form";
        }
        Users users = this.userService.getUser(principal.getName());
        articleService.create_img(articleForm.getTitle(), articleForm.getContent(), users.getAddress_1st(),files);
        return "redirect:/article/list"; // 질문 저장 후 질문 목록으로 이동
    }

    @GetMapping("/modify/{id}")
    public String articleModify(ArticleForm articleForm, @PathVariable(name = "id") Long id) {

        Article article = this.articleService.getArticle(id);

        articleForm.setId(article.getId());
        articleForm.setTitle(article.getTitle());
        articleForm.setContent(article.getContent());
        articleForm.setUpdate(article.getUpdate());
        articleForm.setAreaName(article.getAreaName());

        return "articleUpdate_form";
    }

    @PostMapping("/modify")
    public String articleModify(Model model, @Valid ArticleForm articleForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        System.out.println("articleForm.getId()  " + articleForm.getId());

        boolean result = articleService.modify(articleForm.getId(), articleForm.getTitle(), articleForm.getContent(), articleForm.getAreaName());

        if (result){
            return String.format("redirect:/article/detail/%s", articleForm.getId());
        } else {
            // 수정실패관련 페이지나 메시지 리턴
            return null; // 여기에 해당코드작성
        }


    }

    @GetMapping("/delete/{id}")
    public String articleDelete(@PathVariable(name = "id") Long id) {
        boolean result = articleService.delete(id);
        if (result){
            return "redirect:/article/list";
        } else {
            // 삭제실패관련 페이지나 메시지 리턴
            return null;
        }
    }

}
