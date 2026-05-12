package com.bizteam3.server.global.auth.jwt;

import java.util.Date;

public interface JwtService {
    String createJwt(Integer userId, String username, String name);
    void validateJwt(String jwt);
    Integer getUserId(String jwt);
    String username(String jwt);
    String name(String jwt);
    String getJti(String jwt);
    Date getExpiresAt(String jwt);
}
