package com.example.hellpyending.article.service;

import com.example.hellpyending.DeleteType;
import com.example.hellpyending.article.domain.Article;
import com.example.hellpyending.article.domain.ArticleHashtag;
import com.example.hellpyending.article.exception.DataNotFoundException;

import com.example.hellpyending.article.repository.ArticleHashtagRepository;

import com.example.hellpyending.article.form.ArticleForm;

import com.example.hellpyending.article.repository.ArticleRepository;
import com.example.hellpyending.user.entity.Users;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleImgService articleImgService;

    public Page<Article> getList(String kw, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("create"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한 페이지에 10까지 가능

        if (kw == null || kw.trim().length() == 0) {
            return articleRepository.findByDeleteYn(DeleteType.NORMAL, pageable);
        }
        return articleRepository.findByTitleContainsAndDeleteYn(kw, DeleteType.NORMAL, pageable);

    }

    public Article getArticle(long id) {
//        return articleRepository.findById(id)
//                .orElseThrow(() -> new DataNotFoundException("no %d question not found,".formatted(id)));
        return articleRepository.findByIdAndDeleteYn(id, DeleteType.NORMAL)
                .orElseThrow(() -> new DataNotFoundException("no %d question not found,".formatted(id)));

    }



    @Transactional
    public void modify(Article article, String title, String content) {


            article.setTitle(title);
            article.setContent(content);
            article.setUpdate(LocalDateTime.now());
            articleRepository.save(article);

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


    @Transactional
    public void create(String title, String content, Users users, List<MultipartFile> files, List<String> tags) throws IOException {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setDeleteYn(DeleteType.NORMAL);
        article.setCreate(LocalDateTime.now());
        article.setUpdate(LocalDateTime.now());

        //// 게시글 등록시 시 군 구 동 까지 등록 가능하게 수정했습니다. 게시글 엔티티도 수정 했습니다.
        article.setAddress_1st(users.getAddress_1st());
        article.setAddress_2st(users.getAddress_2st());
        article.setAddress_3st(users.getAddress_3st());// 지역명은 회원가입 할 때 가져오는 것, 조회수는 생각 해보자
        article.setUsers(users);
        articleRepository.save(article);

        long article_id = articleRepository.last_insert_id();


        // 업로드한 이미지가 없을경우
        if(files.get(0).getOriginalFilename().equals("")){
        }
        else{
            insertImgFile(article_id, files);
        }

        //게시글에 해시태그를 포함 할 경우
        if(tags.size()!=0){
            insertHashTag(article,tags);
        }



    }

    private void insertHashTag(Article article_id, List<String> tags) {

        for(String tag : tags){
            article_id.addArticleHashtag(tag);

        }
        articleRepository.save(article_id);
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




    @Transactional
    public void setHitCount(Article article) {
        Integer PrevHitCount = article.getHitCount();
        PrevHitCount++;
        article.setHitCount(PrevHitCount);
        articleRepository.save(article);
    }

}

