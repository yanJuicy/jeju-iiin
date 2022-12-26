package com.example.jejuiiin.controller.response.message;

import lombok.Getter;

@Getter
public enum SuccessMessage {

    /* Product */
    FIND_SUCCESS_CATEGORY_PRODUCT_MSG(200,"카테고리별 상품 조회 성공");


    private final int httpStatusCode;
    private final String msg;

    SuccessMessage(final int httpStatusCode, final String msg) {
        this.httpStatusCode = httpStatusCode;
        this.msg = msg;
    }
}
