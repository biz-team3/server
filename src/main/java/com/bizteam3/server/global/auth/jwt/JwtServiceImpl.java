package com.bizteam3.server.global.auth.jwt;

import com.bizteam3.server.global.exception.ErrorCode;
import com.bizteam3.server.global.exception.common.UnauthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;

    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtProperties.secret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Date getExpiration(long expirationMs) {
        return new Date(System.currentTimeMillis() + expirationMs);
    }

    public String createJwt(Integer userId, String username, String name){
        String jwt = Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("username", username)
                .claim("name", name)
                .expiration(getExpiration(jwtProperties.accessTokenExpirationMs()))
                .signWith(getSigningKey())
                .compact();
        return jwt;
    }

    public void validateJwt(String jwt) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(jwt);

        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(
                    ErrorCode.UNAUTHORIZED,
                    "토큰이 만료되었습니다."
            );

        } catch (MalformedJwtException e) {
            throw new UnauthorizedException(
                    ErrorCode.UNAUTHORIZED,
                    "잘못된 형식의 토큰입니다."
            );

        } catch (UnsupportedJwtException e) {
            throw new UnauthorizedException(
                    ErrorCode.UNAUTHORIZED,
                    "지원하지 않는 토큰입니다."
            );

        } catch (SecurityException e) {
            throw new UnauthorizedException(
                    ErrorCode.UNAUTHORIZED,
                    "토큰 서명이 유효하지 않습니다."
            );

        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException(
                    ErrorCode.UNAUTHORIZED,
                    "토큰 값이 비어있습니다."
            );
        }
    }

    public Integer getUserId(String jwt) {
        return Integer.valueOf(getClaims(jwt).getSubject());
    }

    public String username(String jwt) {
        return getClaims(jwt).get("username", String.class);
    }

    public String name(String jwt) {
        return getClaims(jwt).get("name", String.class);
    }

    private Claims getClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }




}
