package com.bizteam3.server.user.auth.service;

import com.bizteam3.server.global.auth.jwt.JwtService;
import com.bizteam3.server.user.auth.dao.TokenBlacklistDao;
import com.bizteam3.server.user.auth.entity.TokenBlacklist;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl implements TokenBlacklistService {
    private final TokenBlacklistDao tokenBlacklistDao;
    private final JwtService jwtService;

    @Override
    @Transactional
    public void logout(String accessToken) {
        tokenBlacklistDao.deleteExpired();

        String jti = jwtService.getJti(accessToken);
        Integer userId = jwtService.getUserId(accessToken);
        Date expiresAt = jwtService.getExpiresAt(accessToken);

        if (tokenBlacklistDao.existsActiveByJti(jti) > 0) {
            return;
        }

        TokenBlacklist tokenBlacklist = new TokenBlacklist(
                jti,
                userId,
                expiresAt
        );

        tokenBlacklistDao.insert(tokenBlacklist);
    }

    @Override
    public boolean isBlacklisted(String jti) {
        return tokenBlacklistDao.existsActiveByJti(jti) > 0;
    }



}
