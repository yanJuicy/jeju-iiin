package com.example.jejuiiin.repository;

import com.example.jejuiiin.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByEmail(String email);
}
