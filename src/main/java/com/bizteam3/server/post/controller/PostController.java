package com.bizteam3.server.post.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizteam3.server.post.dto.CreatePostRequest;
import com.bizteam3.server.post.dto.CreatePostResponse;
import com.bizteam3.server.post.service.PostService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/posts")
public class PostController {
	//생성자가 한개면 자동으로 주입을 해준다.
	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping
	public CreatePostResponse createPost(
		@RequestBody CreatePostRequest request
		) {
		//TODO: jwt 인증 -> accessToken -> 해시를 풀고 -> claim (userId)
		Integer authorId = 1;

		return postService.createPost(request, authorId);
	}

}
