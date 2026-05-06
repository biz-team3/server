package com.bizteam3.server.post.dto;

import java.util.Set;

import com.bizteam3.server.author.entity.Author;
import com.bizteam3.server.post.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePostResponse {
	Integer postId;
	AuthorResponse author;
	String caption;
	String translatedCaption;
	Set<String> hashtags;

	public static CreatePostResponse toDto(Post post, Author author) {
		return new CreatePostResponse(
			post.getPostId(),
			AuthorResponse.toDto(author),
			post.getCaption(),
			post.getTranslatedCaption(),
			post.getHashtags()
		);
	}
}
