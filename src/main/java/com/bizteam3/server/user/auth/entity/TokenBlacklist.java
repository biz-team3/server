package com.bizteam3.server.user.auth.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

public class TokenBlacklist {
    private Long blacklistId;
    private String jti;
    private Integer userId;
    private Date expiresAt;
    private Date createdAt;

    public TokenBlacklist(String jti, Integer userId, Date expiresAt) {
        this.jti = jti;
        this.userId = userId;
        this.expiresAt = expiresAt;
    }
}
