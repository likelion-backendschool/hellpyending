package com.example.hellpyending.article.service;

import com.example.hellpyending.DeleteType;
import com.example.hellpyending.article.domain.Article;
import com.example.hellpyending.article.domain.ArticleImg;
import com.example.hellpyending.article.repository.ArticleCommentRepository;
import com.example.hellpyending.article.repository.ArticleImgRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleImgService {
    private final ArticleImgRepository articleImgRepository;
    @Value("C:\\Users\\dvum0\\Desktop\\사이드프로젝트\\멋사\\1차프로젝트헬피엔딩\\project_version\\fromgit\\src\\main\\resources\\img\\")
    private String localAddress;



    public File post_img(Article article, MultipartFile multipartFile) throws IOException {
        String fileName = getUUID();
        String[] file_extension = multipartFile.getOriginalFilename().split("\\.");

        File file = new File(localAddress +fileName + "."+file_extension[1]);
        ArticleImg articleImg = new ArticleImg();
        articleImg.setCreate(LocalDateTime.now());
        articleImg.setUpdate(LocalDateTime.now());
        articleImg.setDeleteYn(DeleteType.NORMAL);
        articleImg.setImageOriginName(multipartFile.getOriginalFilename());
        articleImg.setImageUrl(file.getPath());
        articleImg.setArticle(article);
        articleImgRepository.save(articleImg);
        multipartFile.transferTo(file);
        return file;
    }
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }
}

