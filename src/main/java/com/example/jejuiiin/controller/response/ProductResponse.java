package com.example.jejuiiin.controller.response;

import com.example.jejuiiin.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {
    private Long productId;
    private String name;
    private int price;
    private String caption;
    private String bigThumbnailImgUrl;
    private String mediumThumbnailImgUrl;
    private String detailImgUrl;
}
