package com.bizteam3.server.post.entity;

import java.time.LocalDateTime;

import com.bizteam3.server.global.exception.common.InvalidParameterException;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Comment {
	Integer commentId;
	Integer postId;
	Integer userId;
	String text;
	LocalDateTime createdAt;
	LocalDateTime updatedAt;
	LocalDateTime deletedAt;

	public Comment(Integer postId, Integer userId, String text) {
		validateText(text);

		this.postId = postId;
		this.userId = userId;
		this.text = text;
	}

	public Comment(Integer commentId, String text) {
		validateText(text);

		this.commentId = commentId;
		this.text = text;
	}

	public void validateText(String text) {
		if(text.length() > 255)
			throw new InvalidParameterException("text는 255글자를 넘을 수 없습니다");
	}
}
