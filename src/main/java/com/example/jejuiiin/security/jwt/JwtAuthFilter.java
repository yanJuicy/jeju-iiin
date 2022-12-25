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
        String uri = request.getRequestURI();

//        /* Login, SignUp, API일 경우 해당 Filter 건너뜀. */
//        if (uri.equals("/api/v1/members/login") || uri.equals("/api/v1/members/signup")) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        String token = jwtUtil.resolveToken(request, JwtUtil.AUTHORIZATION_ACCESS);

        /* Token 유효성 검사 및 인증 */
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtUtil.validateAccessToken(token, request, response)) {
            throw new CustomSecurityException(INVALID_TOKEN_MSG);
        }

        Claims info = jwtUtil.getUserInfoFromHttpServletRequest(request);
        setAuthentication(info.getSubject());

        filterChain.doFilter(request, response);
    }

    /* 인증/인가 설정 */
    private void setAuthentication(String loginId) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(loginId);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
