package com.bizteam3.server.profile.dto;

import com.bizteam3.server.user.entity.AccountVisType;
import com.bizteam3.server.user.entity.User;
import lombok.Data;

@Data
public class ProfileRequest {
    private String username;
    private String name;
    private String bio;
    private String website;
    private String profileImg;
    private String profileImageUrl;
    private AccountVisType accountVis;
    private String accountVisibility;

    public String resolveProfileImg() {
        return profileImg != null ? profileImg : profileImageUrl;
    }

    public AccountVisType resolveAccountVis() {
        if (accountVis != null) {
            return accountVis;
        }

        if (accountVisibility == null || accountVisibility.isBlank()) {
            return null;
        }

        return AccountVisType.valueOf(accountVisibility);
    }

    public User toEntity(Integer userId) {
        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);
        user.setName(name);
        user.setBio(bio);
        user.setWebsite(website);
        user.setProfileImg(resolveProfileImg());
        user.setAccountVis(resolveAccountVis());
        return user;
    }
}
