package com.bizteam3.server.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContentResponse {
	Integer postId;
	String imageUrl;
	Integer mediaCount;
	Integer likeCount;
	Integer commentCount;

	public static ContentResponse toDto(
		Integer postId,
		String imageUrl,
		Integer mediaCount,
		Integer likeCount,
		Integer commentCount
		) {
		return new ContentResponse(
			postId,
			imageUrl,
			mediaCount,
			likeCount,
			commentCount
		);
	}
}
