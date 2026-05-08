package com.bizteam3.server.post.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizteam3.server.post.dto.CommentCreateRequest;
import com.bizteam3.server.post.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService service;


	@PostMapping("/{postId}/comments")
	public void createComment(
		@PathVariable Integer postId,
		@Valid @RequestBody CommentCreateRequest request
	) {
		// TODO: JWT
		Integer userId = 1;
		service.create(postId, userId, request);
	}
}
