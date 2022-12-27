package com.example.jejuiiin.repository;

import com.example.jejuiiin.domain.CartItem;
import com.example.jejuiiin.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByProductIdAndMember(Long productId, Member member);

    int countByMember(Member member);
}
