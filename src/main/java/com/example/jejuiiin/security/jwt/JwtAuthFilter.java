package com.example.jejuiiin.security.jwt;

import com.example.jejuiiin.security.jwt.exception.CustomSecurityException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.jejuiiin.exception.ExceptionMessage.INVALID_TOKEN_MSG;
import static com.example.jejuiiin.exception.ExceptionMessage.TOKEN_NOT_FOUND_MSG;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, CustomSecurityException {
        /* Filter가 적용되고 있는 uri 추출 */
        String uri = request.getRequestURI();

        /* Login, SignUp, API일 경우 해당 Filter 건너뜀. */
        if (uri.equals("/api/v1/members/login") || uri.equals("/api/v1/members/signup")) {
            filterChain.doFilter(request, response);
            return;
        }


        /* 1. Request에서 토큰 추출 */
        String token = jwtUtil.resolveToken(request, "authorization");

        /* 2. Token 유효성 검사 및 인증 */
        /* 2-1. Token 존재 여부 확인 */
        if (token == null) {
            throw new CustomSecurityException(TOKEN_NOT_FOUND_MSG);
        }
        /* 2-2. Token 유효성 확인 */
        if (!jwtUtil.validateAccessToken(token, request, response)) {
            throw new CustomSecurityException(INVALID_TOKEN_MSG);
        }
        /* 3. 사용자 인증 */
        Claims info = jwtUtil.getUserInfoFromHttpServletRequest(request);
        setAuthentication(info.getSubject());

        /* 4. 다음 필터로 보냄 */
        filterChain.doFilter(request, response);
    }

    /* 인증/인가 설정 */
    private void setAuthentication(String loginId) {
        /* 1. Security Context 생성 */
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        /* 2. Authentication 생성 */
        Authentication authentication = jwtUtil.createAuthentication(loginId);
        /* 3. Context에 Authentication 넣기 */
        context.setAuthentication(authentication);
        /* 4. Security Context Holder에 Context 넣기 */
        SecurityContextHolder.setContext(context);
    }
}
