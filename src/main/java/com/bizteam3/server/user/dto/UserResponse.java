package com.bizteam3.server.user.dto;

import com.bizteam3.server.user.entity.AccountVisType;
import com.bizteam3.server.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * none password
 * */
@Data
@Builder
public class UserResponse {
	private final Integer userId;
	private final String username;
	private final String name;
	private final String bio;
	private final String website;
	private final String profileImg;
	private final AccountVisType accountVis;
	private final LocalDateTime createdAt;
	private final LocalDateTime updateAt;
	private final LocalDateTime deleteAt;

	public static List<UserResponse> toFindDto(List<User> users) {
		return users.stream()
			.map(user -> UserResponse.builder()
				.userId(user.getUserId())
				.username(user.getUsername())
				.name(user.getName())
				.bio(user.getBio())
				.website(user.getWebsite())
				.profileImg(user.getProfileImg())
				.accountVis(user.getAccountVis())
				.build())
			.toList();
	}
}