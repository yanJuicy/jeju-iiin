package com.example.jejuiiin.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessage {

    /* Member */
    DUPLICATE_LOGIN_ID_MSG(400, "이미 가입한 아이디입니다."),
    DUPLICATE_EMAIL_MSG(400, "이미 가입한 이메일입니다."),

    /*Jwt, Security */
    TOKEN_NOT_FOUND_MSG(401,"토큰이 존재하지 않습니다."),
    INVALID_TOKEN_MSG(401,"토큰이 유효하지 않습니다."),

    /* Product */
    INVALID_PAGE_NUMBER_MSG(400,"페이지 값은 숫자입니다.");


    private final int statusCode;
    private final String msg;

    ExceptionMessage(final int statusCode, final String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
