package com.example.jejuiiin.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {
    private Long productId;
    private String name;
    private int price;
    private String caption;
    private String thumbnailImgUrl;
    private String detailImgUrl;
}
