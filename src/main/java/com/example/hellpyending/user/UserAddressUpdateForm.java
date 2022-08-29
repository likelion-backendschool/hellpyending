package com.example.hellpyending.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserAddressUpdateForm {
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
