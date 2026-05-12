package com.bizteam3.server.user.auth.dao;

import com.bizteam3.server.user.auth.entity.TokenBlacklist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TokenBlacklistDao {
    int insert(TokenBlacklist tokenBlacklist);

    int existsActiveByJti(@Param("jti") String jti);

    int deleteExpired();
}
