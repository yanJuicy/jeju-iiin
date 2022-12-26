package com.example.jejuiiin.repository;

import com.example.jejuiiin.domain.Product;
import com.example.jejuiiin.domain.ProductCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findTop5ByOrderByProductIdDesc(); //신규 상품 기준 5개 노출

    Page<Product> findAllByCategoryOrderByProductIdDesc(@Param("category") ProductCategory category, Pageable pageable);
}
