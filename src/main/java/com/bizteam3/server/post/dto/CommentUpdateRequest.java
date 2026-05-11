package com.bizteam3.server.post.dto;

import com.bizteam3.server.post.entity.Comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentUpdateRequest {
	@NotBlank
	String text;

	public static Comment toEntity(Integer commentId, CommentUpdateRequest request) {
		return new Comment(
			commentId,
			request.getText()
		);
	}
}
