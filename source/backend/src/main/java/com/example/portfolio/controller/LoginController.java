package com.example.portfolio.controller;

import com.example.portfolio.config.JwtTokenProvider;
import com.example.portfolio.dto.LoginRequest;
import com.example.portfolio.service.LoginHistoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final LoginHistoryService loginHistoryService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginController(LoginHistoryService loginHistoryService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.loginHistoryService = loginHistoryService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * 로그인 API
     * @param loginRequest 로그인 요청 정보
     * @param request HTTP 요청 정보
     * @return accessToken 발급된 JWT 토큰
     */
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        // 인증 정보 가져오기
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword())
        );

        // 인증 실패 시 에러 발생
        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("로그인에 실패하였습니다.");
        }

        // 액세스 토큰 발급
        String accessToken = jwtTokenProvider.createToken(authentication.getName());

        // 로그인 이력 저장
        loginHistoryService.recordSuccess(loginRequest, request);

        return Map.of("accessToken", accessToken);
    }

    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return Map.of("authenticated", false);
        }

        return Map.of(
                "authenticated", true,
                "username", authentication.getName()
        );
    }
}