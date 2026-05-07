package com.bizteam3.server.user.dto;

import com.bizteam3.server.user.entity.AccountVisType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Integer userId;
    private String username;
    private String password;
    private String name;
    private String bio;
    private String website;
    private String profileImg;
    private AccountVisType accountVis;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;

}

//Integer userId;
//String username;
//String password;
//String name;
//String bio;
//String website;
//String profileImg;
//AccountVisType accountVis;
//LocalDateTime createdAt;
//LocalDateTime updateAt;
//LocalDateTime deleteAt;

//response.setUserId(modifiedUser.getUserId());
//response.setPassword(modifiedUser.getPassword());
//response.setName(modifiedUser.getName());
//response.setBio(modifiedUser.getBio());
//response.setWebsite(modifiedUser.getWebsite());
//response.setProfileImg(modifiedUser.getProfileImg());
//response.setAccountVis(modifiedUser.getAccountVis());
//response.setUpdateAt(modifiedUser.getUpdateAt());