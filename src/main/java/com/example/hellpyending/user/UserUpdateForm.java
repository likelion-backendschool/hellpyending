package com.example.hellpyending.user;

import com.example.hellpyending.user.entity.Sex;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserUpdateForm {

    @Size(min = 3, max = 25, message = "사용자 닉네임 길이는 3자 이상 25자 이하 이여야 합니다.")
    @NotEmpty(message = "사용자 닉네임은 필수항목입니다.")
    private String nickname;

    @NotEmpty(message = "현재 비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "신규 비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "휴대폰 번호는 필수항목입니다.")
    @Size(max = 30)
    private String phoneNumber;

    @NotEmpty(message = "광역시는 필수항목입니다.")
    private String address_1st;

    @NotEmpty(message = "시군구는 필수항목입니다.")
    private String address_2st;

    @NotEmpty(message = "동읍면리는 필수항목입니다.")
    private String address_3st;

    @NotEmpty(message = "도로명은 필수항목입니다.")
    private String address_4st;

    @NotEmpty(message = "상세 주소는 필수항목입니다.")
    private String address_detail;
}
