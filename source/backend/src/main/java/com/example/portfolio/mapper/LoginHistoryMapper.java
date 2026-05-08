package com.example.portfolio.mapper;

import com.example.portfolio.domain.LoginHistory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginHistoryMapper {
    @Insert("""
        INSERT INTO portfolio.login_history (
            user_id,
            login_result,
            fail_reason,
            ip_address,
            user_agent,
            login_at
        )
        VALUES (
            #{userId},
            #{loginResult},
            #{failReason},
            #{ipAddress},
            #{userAgent},
            CURRENT_TIMESTAMP
        )
    """)
    void insert(LoginHistory loginHistory);
}