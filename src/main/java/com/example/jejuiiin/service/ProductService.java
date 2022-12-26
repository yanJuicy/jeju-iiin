package com.example.jejuiiin.service;

import com.example.jejuiiin.controller.request.FindCategoryProductRequest;
import com.example.jejuiiin.controller.response.ProductResponse;
import com.example.jejuiiin.domain.Product;
import com.example.jejuiiin.domain.ProductCategory;
import com.example.jejuiiin.mapper.ProductDetailResponse;
import com.example.jejuiiin.mapper.ProductMapper;
import com.example.jejuiiin.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    /* 새 상품 나열하기 */
    public List<ProductResponse> getNewProducts() {
        List<ProductResponse> list = new ArrayList<>();
        List<Product> products = productRepository.findTop5ByOrderByProductIdDesc();
        for (Product product : products) {
            list.add(productMapper.toResponse(product));
        }
        return list;
    }

    public ProductDetailResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NoSuchElementException());
        return productMapper.toDetailResponse(product);
    }

    @Transactional(readOnly = true)
    public Page<Product> getCategoryProducts(FindCategoryProductRequest request) {
        String category = request.getCategory().toUpperCase();
        ProductCategory categoryEnum = null;
        try {
            categoryEnum = ProductCategory.valueOf(category);
        } catch (IllegalArgumentException ignored) {                        /* category 값이 잘못되면 null로 바인딩 어차피 sql에서 오류 안남 */
        }

        int page = request.getPage();
        int size = request.getSize();
        Pageable pageable = PageRequest.of(page - 1, size);

        return productRepository.findAllByCategoryOrderByProductIdDesc(categoryEnum, pageable);
    }

}
