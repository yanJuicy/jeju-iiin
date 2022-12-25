package com.example.jejuiiin.repository;

import com.example.jejuiiin.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
     List<Product> findTop5ByOrderByProductIdDesc(); //신규 상품 기준 5개 노출
}
