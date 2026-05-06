package com.bizteam3.server.post.entity;

import lombok.Data;

@Data
public class Media {
	MediaType type;
	String url;
	int sortOrder;
	String originalFileName;

	public Media(MediaType type, String url, int sortOrder, String originalFileName) {
		this.type = type;
		this.url = url;
		this.sortOrder = sortOrder;
		this.originalFileName = originalFileName;
	}
}

