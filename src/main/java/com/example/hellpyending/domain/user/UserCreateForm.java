package com.example.hellpyending.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자 ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Size(max = 400)
    @Email
    private String email;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    @Size(max = 30)
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    @Size(max = 30)
    private String password2;

    @NotEmpty(message = "휴대폰 번호는 필수항목입니다.")
    @Size(max = 30)
    private String phoneNumber;

    @NotEmpty(message = "성별은 필수항목입니다.")
    private Sex sex;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Size(max = 255)
    private String address;

    @NotEmpty(message = "생년월일은 필수항목입니다.")
    private String birthday;

}