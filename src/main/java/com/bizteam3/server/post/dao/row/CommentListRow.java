package com.bizteam3.server.post.dao.row;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentListRow {
	private Integer commentId;
	private Integer postId;
	private Integer userId;
	private String userName;
	private String text;
	private LocalDateTime createdAt;
}
