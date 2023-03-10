package com.example.jejuiiin.service;

import static com.example.jejuiiin.domain.ProductCategory.MAGAZINE;
import static com.example.jejuiiin.domain.ProductCategory.SHOP;
import static com.example.jejuiiin.domain.ProductSubCategory.FINDERS;
import static com.example.jejuiiin.domain.ProductSubCategory.IIIN;
import static com.example.jejuiiin.mapper.ProductMapperMapStruct.PRODUCT_MAPPER;

import com.example.jejuiiin.controller.request.FindCategoryProductRequest;
import com.example.jejuiiin.controller.response.ProductResponse;
import com.example.jejuiiin.domain.Product;
import com.example.jejuiiin.domain.ProductCategory;
import com.example.jejuiiin.domain.ProductSubCategory;
import com.example.jejuiiin.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /* 새 상품 나열하기 */
    public List<ProductResponse> getNewProducts() {
        List<Product> products = productRepository.findTop4ByOrderByProductIdDesc();
        return products.stream()
                .map(PRODUCT_MAPPER::productToProductResponse)
                .toList();
    }

    public ProductResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException());
        return PRODUCT_MAPPER.productToProductResponse(product);
    }

    @Transactional(readOnly = true)
    public Page<Product> getCategoryProducts(FindCategoryProductRequest request) {
        ProductCategory category = resolveCategory(request.getCategory());
        ProductSubCategory subCategory = resolveSubCategory(request.getSubCategory());

        int page = request.getPage();
        int size = request.getSize();
        Pageable pageable = PageRequest.of(page - 1, size);

        return productRepository.findAllByCategoryOrSubCategoryOrderByProductIdDesc(category, subCategory, pageable);
    }

    private ProductCategory resolveCategory(String category) {
        ProductCategory categoryEnum = null;
        try {
            categoryEnum = ProductCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException ignored) {                  /* 요청 url의 category 값이 잘못되면 null로 바인딩, sql에서는 빈 리스트 반환 */
        }
        return categoryEnum;
    }

    private ProductSubCategory resolveSubCategory(String subCategory) {
        ProductSubCategory subCategoryEnum = null;
        try {
            subCategoryEnum = ProductSubCategory.valueOf(subCategory.toUpperCase());
        } catch (IllegalArgumentException ignored) {
        }
        return subCategoryEnum;
    }

    public List<String> getSubCategoryList(String category, String subCategory) {
        List<String> magazineSubCategory = List.of("IIIN", "FINDERS");
        List<String> shopSubCategory = List.of("ART", "BOOK", "FOOD", "GOODS", "HANLIMSUGIC", "RESERVATION");

        if (category.equalsIgnoreCase(MAGAZINE.name())) {
            return magazineSubCategory;
        }
        if (category.equalsIgnoreCase(SHOP.name())) {
            return shopSubCategory;
        }

        ProductSubCategory productSubCategory;
        try {
            productSubCategory = ProductSubCategory.valueOf(subCategory.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            return Collections.emptyList();
        }

        if (productSubCategory == IIIN || productSubCategory == FINDERS) {
            return magazineSubCategory;
        }

        return shopSubCategory;
    }

}
