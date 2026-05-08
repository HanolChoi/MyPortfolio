package com.example.portfolio.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private Long id;
    private String userId;
    private String password;
    private String username;
    private String role;
    private String status;
}