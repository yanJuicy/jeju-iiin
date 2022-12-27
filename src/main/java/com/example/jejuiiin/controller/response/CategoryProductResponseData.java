package com.example.jejuiiin.controller.response;

import lombok.Getter;

@Getter
public class CategoryProductResponseData {
    private Long productId;
    private String name;
    private int price;
    private String caption;
    private String thumbnailImgUrl;
    private String category;
    private String subCategory;

    public CategoryProductResponseData(Long productId, String name, int price, String caption,
                                   String thumbnailImgUrl, String category, String subCategory) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.caption = caption;
        this.thumbnailImgUrl = thumbnailImgUrl;
        this.category = category;
        this.subCategory = subCategory;
    }
}
