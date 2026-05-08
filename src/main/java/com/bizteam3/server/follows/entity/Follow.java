package com.bizteam3.server.follows.entity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * follows 테이블과 매핑되는 팔로우 관계 엔티티입니다.
 */
@Data
@NoArgsConstructor
public class Follow {
	private Integer followId;
	private Integer followerUserId;
	private Integer followingUserId;
	private LocalDateTime createdAt;

	public Follow(Integer followerUserId, Integer followingUserId) {
		this.followerUserId = followerUserId;
		this.followingUserId = followingUserId;
	}
}
