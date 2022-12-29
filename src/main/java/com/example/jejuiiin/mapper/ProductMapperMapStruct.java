package com.example.jejuiiin.mapper;

import com.example.jejuiiin.controller.response.CategoryProductResponseData;
import com.example.jejuiiin.controller.response.ProductResponse;
import com.example.jejuiiin.domain.Product;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapperMapStruct {

    ProductMapperMapStruct PRODUCT_MAPPER = Mappers.getMapper(ProductMapperMapStruct.class);

    CategoryProductResponseData productToCategoryProductResponse(Product product);

    ProductResponse productToProductResponse(Product product);
}
