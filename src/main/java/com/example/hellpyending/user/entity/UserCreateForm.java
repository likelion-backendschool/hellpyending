package com.example.hellpyending.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3, max = 25, message = "사용자 ID 길이는 3자 이상 25자 이하 이여야 합니다.")
    @NotEmpty(message = "사용자 ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Size(max = 400, message = "이메일 길이는 400자 이하 이여야 합니다.")
    @Email
    private String email;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "휴대폰 번호는 필수항목입니다.")
    @Size(max = 30)
    private String phoneNumber;

    @NotNull(message = "성별은 필수항목입니다.")
    private Sex sex;

    @NotEmpty(message = "생년월일은 필수항목입니다.")
    private String year;

    @NotEmpty(message = "생년월일은 필수항목입니다.")
    private String month;

    @NotEmpty(message = "생년월일은 필수항목입니다.")
    private String day;

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