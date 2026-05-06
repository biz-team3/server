package com.bizteam3.server.post.dto;

import com.bizteam3.server.author.entity.Author;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorResponse {
	Integer authorId;
	String username;
	String displayName;
	String profileImageUrl;
	Boolean hasActiveStory;
	Boolean isViewer;

	public static AuthorResponse toDto(Author author) {
		return new AuthorResponse(
			author.getAuthorId(),
			author.getUsername(),
			author.getDisplayName(),
			author.getProfileImageUrl(),
			author.getHasActiveStory(),
			author.getIsViewer()
		);
	}
}
