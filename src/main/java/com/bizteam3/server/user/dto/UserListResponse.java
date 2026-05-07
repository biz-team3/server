package com.bizteam3.server.user.dto;

import java.util.List;

public record UserListResponse(
        List<UserResponse> users,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext
) {
}
