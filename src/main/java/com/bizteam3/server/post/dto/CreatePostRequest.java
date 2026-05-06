package com.bizteam3.server.post.dto;

import java.util.List;
import java.util.Set;

import com.bizteam3.server.post.entity.Media;

import lombok.Data;

@Data
public class CreatePostRequest {
	List<MediaRequest> media;
	String caption;
	String translatedCaption;
	Set<String> hashtags;


	public List<Media> toMediaList() {
		return media.stream()
			.map(MediaRequest::toEntity)
			.toList();
	}
}
