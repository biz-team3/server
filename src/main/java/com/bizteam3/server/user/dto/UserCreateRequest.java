package com.bizteam3.server.user.dto;

import java.util.List;

import com.bizteam3.server.user.entity.AccountVisType;
import com.bizteam3.server.user.entity.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCreateRequest {
    @NotBlank(message = "username은 필수입니다.")
    private String username;
    private String password;
    private String name;
    private String bio;
    private String website;
    private String profileImageUrl;
    private List<String> profileImageUrls;
    private String accountVisibility;

    public static User toEntity(UserCreateRequest request){
        return new User(
                request.getUsername().trim(),
                defaultString(request.getPassword(), "password"),
                defaultString(request.getName(), request.getUsername().trim()),
                request.getBio(),
                request.getWebsite(),
                firstProfileImage(request),
                AccountVisType.PRIVATE.name().equals(request.getAccountVisibility()) ?
                        AccountVisType.PRIVATE : AccountVisType.PUBLIC
        );
    }

    private static String firstProfileImage(UserCreateRequest request) {
        if (request.getProfileImageUrl() != null && !request.getProfileImageUrl().isBlank()) {
            return request.getProfileImageUrl();
        }
        if (request.getProfileImageUrls() == null || request.getProfileImageUrls().isEmpty()) {
            return "";
        }
        return request.getProfileImageUrls().get(0);
    }

    private static String defaultString(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }
}
