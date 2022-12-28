package com.example.jejuiiin.repository;

import com.example.jejuiiin.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
