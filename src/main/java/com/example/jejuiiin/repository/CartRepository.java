package com.example.jejuiiin.repository;

import com.example.jejuiiin.domain.CartItem;
import com.example.jejuiiin.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByProductIdAndMember(Long productId, Member member);

    int countByMember(Member member);

    List<CartItem> findAllByMember(Member member);

    @Modifying
    @Query("delete from CartItem c where c.productId in :ids")
    void deleteAllByProductIdInQuery(@Param("ids") List<Long> ids);
}
