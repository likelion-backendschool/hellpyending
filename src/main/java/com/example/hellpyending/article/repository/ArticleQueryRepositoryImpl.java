package com.example.hellpyending.article.repository;

import com.example.hellpyending.article.domain.Article;
import com.example.hellpyending.user.entity.DeleteType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.example.hellpyending.article.domain.QArticle.article;
import static com.example.hellpyending.article.domain.QArticleHashtag.articleHashtag;
import static com.example.hellpyending.user.entity.QUsers.users;

public class ArticleQueryRepositoryImpl extends RepositorySupport implements ArticleQueryRepository {

    public ArticleQueryRepositoryImpl() {
        super(Article.class);
    }
    @Override
    public Page<Article> findByUserContainsAndDeleteYn(String kw, DeleteType deleteYn, Pageable pageable) {
        return applyPagination(pageable, query ->
                query.select(article)
                        .from(article)
                        .leftJoin(article.users, users)
                        .where(article.users.nickname.contains(kw)));
    }
    @Override
    public Page<Article> findByHashTagContainsAndDeleteYn(String kw, DeleteType deleteYn, Pageable pageable) {
        return applyPagination(pageable, query ->
                query.select(article)
                        .from(article)
                        .leftJoin(article.articleHashtags, articleHashtag)
                        .where(articleHashtag.hashtagName.eq(kw)));
    }
}
