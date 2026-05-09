package com.example.portfolio.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final long TOKEN_VALIDITY_SECONDS = 1000 * 60 * 30; // 30분
    private final SecretKey key;
    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret, UserDetailsService userDetailsService) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userDetailsService = userDetailsService;
    }

    /**
     * JWT 토큰 생성
     * @param userId 사용자 ID
     * @return 생성된 JWT 토큰
     */
    public String createToken(String userId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + TOKEN_VALIDITY_SECONDS);

        return Jwts.builder()
                .subject(userId)
                .issuedAt(now)
                .expiration(expiration) // 30분
                .signWith(key)
                .compact();
    }

    /**
     * JWT 토큰 유효성 검증
     * @param token 검증할 JWT 토큰
     * @return 유효성 여부
     */
    public boolean validateToken(String token) {
        try {
            parseClaims(token);

            return true;
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    /**
     * JWT 토큰에서 사용자 ID 추출
     * @param token JWT 토큰
     * @return 사용자 ID
     */
    public Authentication getAuthentication(String token) {
        String userId = getUserId(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    /**
     * JWT 토큰에서 사용자 ID 추출
     * @param token JWT 토큰
     * @return 사용자 ID
     */
    private String getUserId(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * JWT 토큰의 클레임을 파싱하여 Claims 객체를 반환
     * @param token JWT 토큰
     * @return Claims 객체
     */
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}