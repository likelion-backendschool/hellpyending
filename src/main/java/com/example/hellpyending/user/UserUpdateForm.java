package com.example.hellpyending.user;

import com.example.hellpyending.user.entity.Sex;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserUpdateForm {

    @Size(min = 3, max = 25, message = "사용자 닉네임 길이는 3자 이상 25자 이하 이여야 합니다.")
    private String nickname;

    private String password1;

    private String password2;

    @Size(max = 30)
    private String phoneNumber;

    private String address_1st;

    private String address_2st;

    private String address_3st;

    private String address_4st;

    private String address_detail;
}
