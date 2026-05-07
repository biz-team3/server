package com.bizteam3.server.user.dto;

import com.bizteam3.server.user.entity.User;

public record UserResponse(
        Integer userId,
        String username,
        String name,
        String bio,
        String website,
        String profileImageUrl,
        long followerCount,
        long followingCount,
        long postCount,
        String accountVisibility
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getName(),
                user.getBio(),
                user.getWebsite(),
                user.getProfileImg(),
                0,
                0,
                0,
                user.getAccountVis() == null ? "PUBLIC" : user.getAccountVis().name()
        );
    }
}
