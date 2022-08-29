package com.example.hellpyending.article.service;

import com.example.hellpyending.DeleteType;
import com.example.hellpyending.article.domain.Article;
import com.example.hellpyending.article.domain.ArticleComment;
import com.example.hellpyending.article.domain.ArticleImg;
import com.example.hellpyending.article.exception.DataNotFoundException;
import com.example.hellpyending.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleImgService articleImgService;

    public Page<Article> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("create"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한 페이지에 10까지 가능

        return articleRepository.findByDeleteYn(DeleteType.NORMAL, pageable);

        // 스트림
//        List<Article> articleList = articleRepository.findAll();
//
//        articleList.forEach(article ->
//                article.getArticleCommentList()
//                        .addAll(articleCommentRepository.findByArticleAndCommentBundle(article, null))
//        );

        // 여러번 쿼리
//        List<Article> articleList = articleRepository.findAll();
//        List<ArticleComment> articleCommentList = articleCommentRepository.findAll();
//
//
//
//        articleList.forEach(article ->
//                article.getArticleCommentList().addAll(articleCommentList.stream().filter(articleComment ->
//                        articleComment.getArticle().getId().equals(article.getId()) && articleComment.getCommentBundle() == null
//                ).toList())
//        ); // 첫번째 성능이 제곱으로 느려짐 댓글마다 필터링을 해야함
    }

    public Article getArticle(long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("no %d question not found,".formatted(id)));
    }

    @Transactional
    public void create(String title, String content, String areaName) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setDeleteYn(DeleteType.NORMAL);
        article.setCreate(LocalDateTime.now());
        article.setUpdate(LocalDateTime.now());
        article.setAreaName(areaName); // 지역명은 회원가입 할 때 가져오는 것, 조회수는 생각 해보자
        articleRepository.save(article);

    }

    @Transactional
    public boolean modify(Long articleId, String title, String content, String areaName) {

        Optional<Article> articleOptional = articleRepository.findByIdAndDeleteYn(articleId, DeleteType.NORMAL);

        if (articleOptional.isEmpty()) { //조회가 안되면 잘못 요청한 것임
            return false;
        } else {
            Article article = articleOptional.get();
            article.setTitle(title);
            article.setContent(content);
            article.setUpdate(LocalDateTime.now());
            article.setAreaName(areaName);

            return true;
        }
    }

    @Transactional
    public boolean delete(Long id) {

        Optional<Article> articleOptional = articleRepository.findByIdAndDeleteYn(id, DeleteType.NORMAL);

        if (articleOptional.isEmpty()) { //조회가 안되면 잘못 요청한 것임
            return false;
        } else {
            Article article = articleOptional.get();
            article.setDeleteYn(DeleteType.DELETE);
            return true;
        }
    }

    public void create_img(String title, String content, String address_1st, List<MultipartFile> files) throws IOException {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setDeleteYn(DeleteType.NORMAL);
        article.setCreate(LocalDateTime.now());
        article.setUpdate(LocalDateTime.now());
        article.setAreaName(address_1st); // 지역명은 회원가입 할 때 가져오는 것, 조회수는 생각 해보자
        articleRepository.save(article);
        long article_id = articleRepository.last_insert_id();
        insertImgFile(article_id, files);
    }

    private long insertImgFile(long article_id, List<MultipartFile> files) throws IOException {
        try {
            Article article = getArticle(article_id);
            if (files != null) {
                for (MultipartFile multipartFile : files) {
                    articleImgService.post_img(article,multipartFile);
                }
            }

        }
        catch (Exception e){

        }
        return article_id;
    }


}