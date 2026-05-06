package com.bizteam3.server.post.entity;


import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class Post {
	Integer postId;
	Integer userId;
	List<Media> mediaList;
	String caption;
	String translatedCaption;
	Set<String> hashtags;

	public Post(Integer postId, Integer userId, List<Media> mediaList, String caption, String translatedCaption,
		Set<String> hashtags) {
		this.postId = postId;
		this.userId = userId;
		this.mediaList = mediaList;
		this.caption = caption;
		this.translatedCaption = translatedCaption;
		this.hashtags = hashtags;
	}

	public Post(Integer userId,
		List<Media> mediaList,
		String caption,
		String translatedCaption,
		Set<String> hashtags
	) {
		this.userId = userId;
		this.mediaList = mediaList;
		this.caption = caption;
		this.translatedCaption = translatedCaption;
		this.hashtags = hashtags;
	}
}
