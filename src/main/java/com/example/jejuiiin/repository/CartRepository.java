package com.example.jejuiiin.repository;

import com.example.jejuiiin.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartItem, Long> {
}
