package com.example.portfolio.service;

import com.example.portfolio.domain.User;
import com.example.portfolio.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserMapper userMapper;

    public CustomUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) {
        User user;

        try {
            user = userMapper.findByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();

            throw e;
        }

        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        boolean isDisabled = !"ACTIVE".equals(user.getStatus());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserId())
                .password(user.getPassword())
                .roles(user.getRole())
                .disabled(isDisabled)
                .build();
    }
}