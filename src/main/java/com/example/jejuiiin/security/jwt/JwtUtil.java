package com.example.jejuiiin.security.jwt;

import com.example.jejuiiin.controller.response.Response;
import com.example.jejuiiin.security.UserDetailsServiceImpl;
import com.example.jejuiiin.security.jwt.exception.CustomSecurityException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static com.example.jejuiiin.controller.exception.ExceptionMessage.*;

@Slf4j
@Component
@PropertySource("classpath:security.properties")
@RequiredArgsConstructor
public class JwtUtil {
    public static final String AUTHORIZATION_ACCESS = "Authorization";
    /* 토큰 생성 시 앞에 붙는 식별자 */
    private static final String BEARER_PREFIX = "Bearer ";
    /* 토큰 만료 시간 (분*초*밀리sec) */
    private static final long TOKEN_TIME = 30 * 60 * 1000L;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    /* JWT SecretKey */
    @Value("${jwt.secret.key.access}")
    private String accessTokenSecretKey;

    private Key accessTokenKey;

    /* 사용할 암호화 알고리즘 설정 */
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] accessTokenBytes = Base64.getDecoder().decode(accessTokenSecretKey);     /* JWT 토큰 값을 Base64 형식을 디코딩 */
        accessTokenKey = Keys.hmacShaKeyFor(accessTokenBytes);     /* key에 넣어줌 */
    }

    /* Token 분해 Method */
    public String resolveToken(HttpServletRequest request, String authorization) {
        String bearerToken = request.getHeader(authorization);     /* AccessToken의 value를 가지고옴 */
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {     /* Bearer 값 있는지 확인 */
            return bearerToken.substring(7);     /* 확인되면 Bearer 빼고 반환 */
        }
        return null;
    }

    /* Access Token 생성 */
    public String createAccessToken(String loginId) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(loginId)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))     /* 만료 시간 */
                        .setIssuedAt(date)     /* 언제 생성되었는지 */
                        .signWith(accessTokenKey, signatureAlgorithm)     /* Key를 어떻게 암호화 할 것인지 */
                        .compact();
    }

    /* Access 토큰 검증 */
    public void validateAccessToken(String accessToken, HttpServletRequest request, HttpServletResponse response) {
        try {
            Jwts.parserBuilder().setSigningKey(accessTokenKey).build().parseClaimsJws(accessToken);
        } catch (SecurityException | MalformedJwtException e) {      /* 유효하지 않는 Access JWT 서명 */
            throw new CustomSecurityException(INVALID_TOKEN_MSG);
        } catch (ExpiredJwtException e) {      /* Access JWT 만료 */
            throw new CustomSecurityException(EXPIRED_TOKEN_MSG);
        } catch (UnsupportedJwtException e) {      /* 지원되지 않는 Access JWT */
            throw new CustomSecurityException(UNSUPPORTED_TOKEN_MSG);
        } catch (IllegalArgumentException e) {      /* Access JWT claims가 비어 있을 경우 */
            throw new CustomSecurityException(EMPTY_TOKEN_MSG);
        }
    }

    /* 토큰에서 사용자 정보 가져오기 */
    public Claims getUserInfoFromHttpServletRequest(HttpServletRequest request) throws CustomSecurityException {
        /* Request에서 Token 가져오기 */
        String token = resolveToken(request, AUTHORIZATION_ACCESS);

        /* Token이 있는지 확인 */
        if (token == null) {
            // Handler 대신에 Handler Filter를 사용하므로, CustomException 재정의
            throw new CustomSecurityException(TOKEN_NOT_FOUND_MSG);
        }

        try {
            return Jwts.parserBuilder().setSigningKey(accessTokenKey).build().parseClaimsJws(token).getBody();
        }
        catch(ExpiredJwtException ex){
            /* JWT 만료로 인한 Exception 발생 시 Exception에서 Claim 그냥 빼옴 */
            return ex.getClaims();
        }
    }

    /* 인증 객체 생성 */
    public Authentication createAuthentication(String memberName) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(memberName);     /* 이름을 통해 사용자 조회 */
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); //userDetail 및 권한 넣어 생성
    }
}
