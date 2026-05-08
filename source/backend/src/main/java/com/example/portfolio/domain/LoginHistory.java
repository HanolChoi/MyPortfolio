package com.example.portfolio.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class LoginHistory {
    private Long id;
    private Long userId;
    private String loginResult;
    private String failReason;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime loginAt;
}