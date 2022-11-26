package com.example.hellpyending.src.article.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ArticleForm {
    private Long id;
    @NotEmpty(message = "제목은 필수항목입니다.")
    @Size(max = 200, message = "제목을 200자 이하로 입력해주세요.")
    private String title;
    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;
    private LocalDateTime create;
    private LocalDateTime update;
    private String areaName;
}
