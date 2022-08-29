package com.example.hellpyending.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserEmailUpdateForm {
    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Size(max = 400, message = "이메일 길이는 400자 이하 이여야 합니다.")
    @Email
    private String email;

}
