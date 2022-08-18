package com.example.hellpyending.article.controller;

import com.example.hellpyending.article.domain.Article;
import com.example.hellpyending.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

//    @GetMapping
//    public String defa() {
//        return "타임리프주소";
//    }






    //테스트용
    @GetMapping("/test")
    public @ResponseBody List<Article> bundle() {

        return articleService.findAll();

    }
}
