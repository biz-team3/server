package com.bizteam3.server.post.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
	Integer userId;
	String username;
	String profileImageUrl;
}
