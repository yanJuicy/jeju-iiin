package com.example.jejuiiin.repository;

import com.example.jejuiiin.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
