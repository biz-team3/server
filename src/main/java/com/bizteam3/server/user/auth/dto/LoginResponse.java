package com.bizteam3.server.user.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public record LoginResponse(
        String accessToken,
        String tokenType,
        AuthUserResponse user
) {
}
