package com.example.jejuiiin.mapper;

import com.example.jejuiiin.controller.response.CategoryProductResponse;
import com.example.jejuiiin.domain.Product;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-26T10:25:14+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
public class ProductMapperMapStructImpl implements ProductMapperMapStruct {

    @Override
    public CategoryProductResponse productToCategoryProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        Long productId = null;
        String name = null;
        int price = 0;
        String caption = null;
        String mediumThumbnailImgUrl = null;
        String category = null;
        String subCategory = null;

        productId = product.getProductId();
        name = product.getName();
        price = product.getPrice();
        caption = product.getCaption();
        mediumThumbnailImgUrl = product.getMediumThumbnailImgUrl();
        if ( product.getCategory() != null ) {
            category = product.getCategory().name();
        }
        if ( product.getSubCategory() != null ) {
            subCategory = product.getSubCategory().name();
        }

        CategoryProductResponse categoryProductResponse = new CategoryProductResponse( productId, name, price, caption, mediumThumbnailImgUrl, category, subCategory );

        return categoryProductResponse;
    }
}
