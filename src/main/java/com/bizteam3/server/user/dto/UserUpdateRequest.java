package com.bizteam3.server.user.dto;

import com.bizteam3.server.user.entity.AccountVisType;
import com.bizteam3.server.user.entity.User;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserUpdateRequest {
	private String username;
	private String password;
	private String name;
	private String bio;
	private String website;
	private String profileImg;
	private String profileImageUrl;
	private AccountVisType accountVis;
	private String accountVisibility;
	private LocalDateTime updateAt;

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
}
