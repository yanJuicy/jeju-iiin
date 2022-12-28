package com.example.jejuiiin.repository;

import com.example.jejuiiin.domain.Member;
import com.example.jejuiiin.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByMember(Member member);
}
