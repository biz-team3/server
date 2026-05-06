package com.bizteam3.server.post.dto;

import com.bizteam3.server.post.entity.Media;
import com.bizteam3.server.post.entity.MediaType;

import lombok.Data;

@Data
public class MediaRequest {
	String type;
	String url;
	int sortOrder;
	String originalFileName;

	public static Media toEntity(MediaRequest request){
		return new Media(
			MediaType.IMAGE.name().equals(request.getType()) ?
				MediaType.IMAGE : MediaType.VIDEO,
			request.getUrl(),
			request.getSortOrder(),
			request.getOriginalFileName()
		);
	}
}
