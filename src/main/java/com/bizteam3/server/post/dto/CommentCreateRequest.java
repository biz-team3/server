package com.bizteam3.server.post.dto;

import com.bizteam3.server.post.entity.Comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentCreateRequest {
	@NotBlank
	String text;

	public static Comment toEntity(Integer postId, Integer userId, CommentCreateRequest request) {
		return new Comment(
			postId,
			userId,
			request.getText()
		);
	}
}
