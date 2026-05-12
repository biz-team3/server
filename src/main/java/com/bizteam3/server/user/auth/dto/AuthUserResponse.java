package com.bizteam3.server.user.auth.dto;

public record AuthUserResponse(
        Integer userId,
        String username,
        String name
) {
}

