package com.example.jejuiiin.controller.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class SignupRequest {
    /* 영문 소문자 or 숫자로 이루어진 4~16자 검증 */
    @Pattern(regexp = "^[a-z0-9]{4,16}$")
    private String loginId;

    /* 비밀번호 검증 추가해야함 */
    private String password;
    private String name;
    private String email;

    @Builder
    public SignupRequest(String loginId, String password, String name, String email) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
    }
}
