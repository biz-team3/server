package com.bizteam3.server.user.dto;

import com.bizteam3.server.user.entity.AccountVisType;
import com.bizteam3.server.user.entity.User;
import lombok.Data;

@Data
public class UserCreateRequest {
    private String username;
    private String name;
    private String bio;
    private String website;
    private String profileImageUrls;
    private String accountVisibility;

    public static User toEntity(UserCreateRequest request){
        return new User(
                request.getUsername(),
                request.getName(),
                request.getBio(),
                request.getWebsite(),
                request.getProfileImageUrls(),
                AccountVisType.PUBLIC.name().equals(request.getAccountVisibility()) ?
                        AccountVisType.PUBLIC : AccountVisType.PRIVATE
        );
    }
}
