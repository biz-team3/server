package com.bizteam3.server.post.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.post.dto.CommentCreateRequest;
import com.bizteam3.server.post.dto.CommentResponse;
import com.bizteam3.server.post.dto.CommentUpdateRequest;
import com.bizteam3.server.post.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService service;

	@GetMapping("/{postId}/comments")
	public PageResponse<CommentResponse> getComments(
		@PathVariable Integer postId,
		PageRequest request
	){
		//TODO: JWT
		Integer userId = 1;
		return service.findComments(postId, userId, request);
	}


	@PostMapping("/{postId}/comments")
	public void createComment(
		@PathVariable Integer postId,
		@Valid @RequestBody CommentCreateRequest request
	) {
		// TODO: JWT
		Integer userId = 1;
		service.create(postId, userId, request);
	}

	@PatchMapping("/comments/{commentId}")
	public void updateComment(
		@PathVariable Integer commentId,
		@Valid @RequestBody CommentUpdateRequest request
	){
		service.update(commentId, request);
	}

	@DeleteMapping("/comments/{commentId}")
	public void deleteComment(
		@PathVariable Integer commentId
	){
		service.delete(commentId);
	}
}
