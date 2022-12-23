package com.example.jejuiiin.repository;

import com.example.jejuiiin.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
