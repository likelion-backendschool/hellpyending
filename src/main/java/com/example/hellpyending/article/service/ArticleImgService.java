package com.example.hellpyending.article.service;

import com.example.hellpyending.user.entity.DeleteType;
import com.example.hellpyending.article.domain.Article;
import com.example.hellpyending.article.domain.ArticleImg;
import com.example.hellpyending.article.repository.ArticleImgRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleImgService {
    private final ArticleImgRepository articleImgRepository;
    private final S3Upload s3Upload;
    private String localAddress;



    public void post_img(Article article, MultipartFile multipartFile) throws IOException {
//        String fileName = getUUID();
//        String[] file_extension = multipartFile.getOriginalFilename().split("\\.");
//
//        File file = new File(localAddress +fileName + "."+file_extension[1]);
        ArticleImg articleImg = new ArticleImg();
        articleImg.setCreate(LocalDateTime.now());
        articleImg.setUpdate(LocalDateTime.now());
        articleImg.setDeleteYn(DeleteType.NORMAL);
        articleImg.setImageOriginName(multipartFile.getOriginalFilename());
        articleImg.setImageUrl(s3Upload.upload(multipartFile.getInputStream(),multipartFile.getOriginalFilename(),multipartFile.getSize()));
        articleImg.setArticle(article);
        articleImgRepository.save(articleImg);
//        multipartFile.transferTo(file);
//        return file;
    }
}

