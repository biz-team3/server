package com.bizteam3.server.author.entity;

import lombok.Data;

@Data
public class Author {
	Integer authorId;
	String username;
	String displayName;
	String profileImageUrl;
	Boolean hasActiveStory;
	Boolean isViewer;
}
