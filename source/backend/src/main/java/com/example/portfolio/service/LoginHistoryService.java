package com.example.portfolio.service;

import com.example.portfolio.domain.LoginHistory;
import com.example.portfolio.domain.User;
import com.example.portfolio.dto.LoginRequest;
import com.example.portfolio.mapper.LoginHistoryMapper;
import com.example.portfolio.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginHistoryService {
    private final UserMapper userMapper;
    private final LoginHistoryMapper loginHistoryMapper;

    public void recordSuccess(LoginRequest dto, HttpServletRequest request) {
        User user = userMapper.findByUserId(dto.getUserId());

        LoginHistory history = createBaseHistory(request);
        history.setLoginResult("SUCCESS");

        if (user != null) {
            history.setUserId(user.getId());
        }

        loginHistoryMapper.insert(history);
    }

    public void recordFailure(LoginRequest dto, HttpServletRequest request, String failReason) {
        User user = userMapper.findByUserId(dto.getUserId());

        LoginHistory history = createBaseHistory(request);
        history.setLoginResult("FAIL");
        history.setFailReason(failReason);

        if (user != null) {
            history.setUserId(user.getId());
        }

        loginHistoryMapper.insert(history);
    }

    private LoginHistory createBaseHistory(HttpServletRequest request) {
        LoginHistory history = new LoginHistory();
        history.setIpAddress(request.getRemoteAddr());
        history.setUserAgent(request.getHeader("User-Agent"));
        return history;
    }
}