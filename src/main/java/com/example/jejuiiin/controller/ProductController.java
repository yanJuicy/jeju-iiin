package com.example.jejuiiin.controller;

import com.example.jejuiiin.controller.response.ProductResponse;
import com.example.jejuiiin.repository.ProductRepository;
import com.example.jejuiiin.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    /* 새 상품 나열하기 */
    @GetMapping("/newitems")
    public List<ProductResponse> getNewProducts(){
        return productService.getNewProducts();
    }
}
