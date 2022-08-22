package com.example.hellpyending.article.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ArticleCommentForm {
    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;

    private LocalDateTime create;
}
