package com.example.jejuiiin.controller.response;

import com.example.jejuiiin.domain.Product;
import lombok.Getter;

@Getter
public class ProductResponse {
    private Long productId;
    private String name;
    private int price;
    private String caption;
    private String thumbnailImgUrl;
    private String detailImgUrl;

    public ProductResponse(Product product) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.caption = product.getCaption();
        this.thumbnailImgUrl = product.getThumbnailImgUrl();
        this.detailImgUrl = product.getDetailImgUrl();

    }
}
