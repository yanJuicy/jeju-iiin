package com.example.jejuiiin.service;

import com.example.jejuiiin.domain.Product;
import com.example.jejuiiin.mapper.ProductDetailResponse;
import com.example.jejuiiin.mapper.ProductMapper;
import com.example.jejuiiin.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDetailResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow( ()-> new NoSuchElementException());
        return productMapper.toResponse(product);

    }
}
