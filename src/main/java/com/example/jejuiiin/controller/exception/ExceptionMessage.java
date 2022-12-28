package com.example.jejuiiin.controller.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessage {

    /* Member */
    DUPLICATE_LOGIN_ID_MSG(400, "이미 가입한 아이디입니다."),
    DUPLICATE_EMAIL_MSG(400, "이미 가입한 이메일입니다."),
    LOGIN_ID_NOT_FOUND_MSG(400, "아이디 또는 비밀번호가 일치하지 않습니다."),  /* 로그인 시 아이디가 다를 경우 */
    DIFFERENT_PASSWORD_MSG(400, "아이디 또는 비밀번호가 일치하지 않습니다."),  /* 로그인 시 아이디는 일치하는데 비밀번호가 다를 경우 */

    /*Jwt, Security */
    TOKEN_NOT_FOUND_MSG(401,"토큰이 존재하지 않습니다."),
    INVALID_TOKEN_MSG(401,"토큰이 유효하지 않습니다."),
    EXPIRED_TOKEN_MSG(401, "토큰이 만료되었습니다."),
    UNSUPPORTED_TOKEN_MSG(401, "지원하지 않는 토큰입니다."),
    EMPTY_TOKEN_MSG(401, "토큰이 비어 있습니다."),

    /* Product */
    NO_EXISTS_PRODUCT_MSG(404,"상품이 존재하지 않습니다."),

    INVALID_PAGE_NUMBER_MSG(400,"페이지 값은 숫자입니다."),

    /* Cart */
    NO_EXISTS_CART_ITEM_MSG(404, "장바구니에 해당 상품이 존재하지 않습니다."),

	/* OAuth */
	FAIL_OAUTH_LOGIN_MSG(500, "소셜 로그인을 실패했습니다.");


	private final int statusCode;
    private final String msg;

    ExceptionMessage(final int statusCode, final String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
