package com.bizteam3.server.post.dto;

import java.time.LocalDateTime;

import com.bizteam3.server.post.dao.row.CommentListRow;
import com.bizteam3.server.post.dto.vo.Author;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponse {
	private Integer commentId;
	private Integer postId;
	private Author author;
	private String text;
	private LocalDateTime createdAt;
	private boolean isOwner;

	public static CommentResponse toDto(CommentListRow row, Integer loginUserId) {
		return CommentResponse.builder()
			.commentId(row.getCommentId())
			.postId(row.getPostId())
			.author(new Author(
				row.getUserId(),
				row.getUserName(),
				row.getProfileImageUrl()
			))
			.text(row.getText())
			.createdAt(row.getCreatedAt())
			.isOwner(row.getUserId().equals(loginUserId))
			.build();
	}

}
