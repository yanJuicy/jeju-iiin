package com.example.jejuiiin.mapper;

import com.example.jejuiiin.domain.Product;
import org.springframework.stereotype.Component;


@Component
public class ProductMapper {

    public static ProductDetailResponse toResponse(Product product){
        return ProductDetailResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .caption(product.getCaption())
                .bigThumbnailImgUrl(product.getBigThumbnailImgUrl())
                .detailImgUrl(product.getDetailImgUrl())
                .build();
    }
}