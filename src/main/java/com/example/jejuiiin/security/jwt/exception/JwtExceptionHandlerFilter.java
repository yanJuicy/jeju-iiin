package com.example.jejuiiin.security.jwt.exception;

import static com.example.jejuiiin.controller.exception.ExceptionMessage.EXPIRED_TOKEN_MSG;

import com.example.jejuiiin.controller.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomSecurityException ex){
            if (ex.getExceptionMessage().equals(EXPIRED_TOKEN_MSG) && isLoginRequest(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            log.error(ex.getExceptionMessage().getMsg());
            setErrorResponse(response, ex);
        }
    }

    private boolean isLoginRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.contains("login");
    }

    private void setErrorResponse(HttpServletResponse httpServletResponse,
                                  CustomSecurityException ex) throws IOException{
        /* 1. Json Type으로 반환할 것 명시 */
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        /* 2. HttpStatus 설정 */
        httpServletResponse.setStatus(ex.getExceptionMessage().getStatusCode());
        /* 3. CustomErrorResponse 생성 */
        Response<CustomSecurityException> response =
                new Response<>(ex.getExceptionMessage());

        try (OutputStream os = httpServletResponse.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, response);
            os.flush();
        }
    }
}
