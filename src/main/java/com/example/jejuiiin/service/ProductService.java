package com.example.jejuiiin.service;

import com.example.jejuiiin.controller.response.ProductResponse;
import com.example.jejuiiin.domain.Product;
import com.example.jejuiiin.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public List<ProductResponse> getProducts() {
        List<ProductResponse> list = new ArrayList<>();
        List<Product> products = productRepository.findTop5ByOrderByProductIdDesc();
        for (Product product : products) {
            list.add(new ProductResponse(product));
        }
        return list;
    }
}
