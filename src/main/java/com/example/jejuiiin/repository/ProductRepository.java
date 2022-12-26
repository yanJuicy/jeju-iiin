package com.example.jejuiiin.repository;

import com.example.jejuiiin.domain.Product;
import com.example.jejuiiin.domain.ProductCategory;

import com.example.jejuiiin.domain.ProductSubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findTop4ByOrderByProductIdDesc(); //신규 상품 기준 4개 노출

    Page<Product> findAllByCategoryOrSubCategoryOrderByProductIdDesc(@Param("category") ProductCategory category,
                                                                     @Param("subCategory") ProductSubCategory subCategory, Pageable pageable);
}
