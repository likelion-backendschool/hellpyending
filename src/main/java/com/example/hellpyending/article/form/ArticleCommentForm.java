package com.example.hellpyending.article.form;

import com.example.hellpyending.article.domain.ArticleComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ArticleCommentForm {
    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;

    private LocalDateTime create;
}
