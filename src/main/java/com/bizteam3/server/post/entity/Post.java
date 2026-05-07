package com.bizteam3.server.post.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
	Integer postId;
	Integer userId;
	String caption;
	String translatedCaption;
	LocalDateTime createdAt;
	LocalDateTime updateAt;
	LocalDateTime deleteAt;

	public Post(Integer userId, String caption, String translatedCaption) {
		this.userId = userId;
		this.caption = caption;
		this.translatedCaption = translatedCaption;
	}

	public Set<String> createHashtag() {
		Set<String> hashtagSet = new HashSet<>();

		Pattern pattern = Pattern.compile("#([가-힣a-zA-Z0-9_]+)");
		Matcher matcher = pattern.matcher(this.caption);

		while (matcher.find()) {
			hashtagSet.add(matcher.group(1));
		}
		return hashtagSet;
	}
}
