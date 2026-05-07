package com.bizteam3.server.user.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String username;
    private String name;
    private String bio;
    private String website;
    private String profileImageUrl;
    private List<String> profileImageUrls;
    private String accountVisibility;

    public String resolveProfileImageUrl() {
        if (profileImageUrl != null && !profileImageUrl.isBlank()) {
            return profileImageUrl;
        }
        if (profileImageUrls == null || profileImageUrls.isEmpty()) {
            return null;
        }
        return profileImageUrls.get(0);
    }
}
