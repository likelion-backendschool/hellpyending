package com.example.hellpyending.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserOauth2CreateForm {
    @NotEmpty(message = "생년월일은 필수항목입니다.")
    private String year;

    @NotEmpty(message = "생년월일은 필수항목입니다.")
    private String month;

    @NotEmpty(message = "생년월일은 필수항목입니다.")
    private String day;

    @NotEmpty(message = "닉네임은 필수항목입니다.")
    private String nickname;

    @NotNull(message = "성별은 필수항목입니다.")
    private Sex sex;

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
