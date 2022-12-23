package com.example.jejuiiin.repository;

import com.example.jejuiiin.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
