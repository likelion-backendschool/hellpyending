package com.example.hellpyending.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3, max = 25)
    @NotBlank(message = "사용자 ID는 필수항목입니다.")
    private String username;

    @NotBlank(message = "이메일은 필수항목입니다.")
    @Size(max = 400)
    @Email
    private String email;

    @NotBlank(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotBlank(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotBlank(message = "휴대폰 번호는 필수항목입니다.")
    @Size(max = 30)
    private String phoneNumber;

    @NotBlank(message = "성별은 필수항목입니다.")
    private Sex sex;

    @NotBlank(message = "생년월일은 필수항목입니다.")
    private String year;

    @NotBlank(message = "생년월일은 필수항목입니다.")
    private String month;

    @NotBlank(message = "생년월일은 필수항목입니다.")
    private String day;

    @NotBlank(message = "광역시는 필수항목입니다.")
    @Size(min = 2,max = 255, message = "광역시는 필수항목입니다.")
    private String address_1st;

    @NotBlank(message = "시군구는 필수항목입니다.")
    @Size(min = 2,max = 255, message = "시군구는 필수항목입니다.")
    private String address_2st;

    @NotBlank(message = "동읍면리는 필수항목입니다.")
    @Size(min = 2,max = 255, message = "동읍면리는 필수항목입니다.")
    private String address_3st;

    @NotBlank(message = "동네는 필수항목입니다.")
    @Size(min = 2,max = 255, message = "동네는 필수항목입니다.")
    private String address_4st;

    @NotBlank(message = "상세 주소는 필수항목입니다.")
    @Size(min = 2,max = 255, message = "상세 주소는 필수항목입니다.")
    private String address_detail;
}