package com.bizteam3.server.user.auth.service;

public interface TokenBlacklistService {
    void logout(String jwt);
    boolean isBlacklisted(String jti);
}
