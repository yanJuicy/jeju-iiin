package com.example.jejuiiin.service;

import com.example.jejuiiin.controller.response.HeaderResponse;
import com.example.jejuiiin.domain.Member;
import com.example.jejuiiin.repository.CartItemRepository;
import com.example.jejuiiin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class HeaderService {
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public HeaderResponse showHeaderDetails(UserDetails userDetails) {
        if(userDetails == null){
            return new HeaderResponse();
        }

        String loginId = userDetails.getUsername();

        Member member = memberRepository.findByLoginId(loginId).get();

        int cartItemCount = cartItemRepository.countByMember(member);

        return new HeaderResponse(true, cartItemCount);
    }
}
