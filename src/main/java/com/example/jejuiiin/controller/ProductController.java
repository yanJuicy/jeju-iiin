package com.example.jejuiiin.controller;


import com.example.jejuiiin.mapper.ProductDetailResponse;
import com.example.jejuiiin.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ProductDetailResponse getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }


}
