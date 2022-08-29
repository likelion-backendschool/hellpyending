package com.example.hellpyending.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserNickNameUpdateForm {
    @Size(min = 3, max = 25, message = "사용자 닉네임 길이는 3자 이상 25자 이하 이여야 합니다.")
    @NotEmpty(message = "사용자 닉네임은 필수항목입니다.")
    private String nickname;
}
