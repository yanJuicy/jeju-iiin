package com.example.jejuiiin.service;


import com.example.jejuiiin.controller.response.ProductResponse;
import com.example.jejuiiin.domain.Product;
import com.example.jejuiiin.mapper.ProductDetailResponse;

import com.example.jejuiiin.mapper.ProductMapper;
import com.example.jejuiiin.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



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
        public ProductDetailResponse getProduct (Long productId){
            Product product = productRepository.findById(productId).orElseThrow(() -> new NoSuchElementException());
            return productMapper.toDetailResponse(product);


        }
    }
