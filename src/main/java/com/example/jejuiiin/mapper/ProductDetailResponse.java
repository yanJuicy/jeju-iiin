package com.example.jejuiiin.mapper;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductDetailResponse {

    private Long productId;
    private String name;
    private int price;
    private String caption;
    private String bigThumbnailImgUrl;
    private String detailImgUrl;

}
