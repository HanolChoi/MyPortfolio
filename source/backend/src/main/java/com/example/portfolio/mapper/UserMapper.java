package com.example.portfolio.mapper;

import com.example.portfolio.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT id, user_id AS userId, password, username, role, status FROM portfolio.users WHERE user_id = #{userId}")
    User findByUserId(String userId);
}
