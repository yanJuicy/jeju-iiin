package com.example.jejuiiin.mapper;


import com.example.jejuiiin.controller.response.ProductResponse;
import com.example.jejuiiin.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public static ProductResponse toResponse(Product product){
        return ProductResponse.builder()

                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .caption(product.getCaption())
                .thumbnailImgUrl(product.getThumbnailImgUrl())
                .detailImgUrl(product.getDetailImgUrl())
                .build();
    }
    public static ProductDetailResponse toDetailResponse(Product product){
        return ProductDetailResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .caption(product.getCaption())
                .thumbnailImgUrl(product.getThumbnailImgUrl())
                .detailImgUrl(product.getDetailImgUrl())
                .build();
    }
}
