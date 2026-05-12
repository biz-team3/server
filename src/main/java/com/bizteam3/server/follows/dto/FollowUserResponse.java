package com.bizteam3.server.follows.dto;

import java.time.LocalDateTime;

import com.bizteam3.server.user.entity.AccountVisType;

import lombok.Builder;
import lombok.Data;

/**
 * 팔로워/팔로잉 목록에서 사용하는 사용자 요약 응답 DTO
 */
@Data
@Builder
public class FollowUserResponse {
	private final Integer userId;
	private final String username;
	private final String name;
	private final String profileImg;
	private final AccountVisType accountVis;
	private final LocalDateTime followedAt;
	private final FollowViewerRelation viewerRelation;
}
