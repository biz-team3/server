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
import com.bizteam3.server.global.auth.annotation.AccessTokenCheck;
import com.bizteam3.server.post.dto.CommentCreateRequest;
import com.bizteam3.server.post.dto.CommentResponse;
import com.bizteam3.server.post.dto.CommentUpdateRequest;
import com.bizteam3.server.post.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService service;

	@GetMapping("/{postId}/comments")
	@AccessTokenCheck
	public PageResponse<CommentResponse> getComments(
		@PathVariable Integer postId,
		PageRequest request,
		HttpServletRequest httpServletRequest
	){
		Integer userId = (Integer) httpServletRequest.getAttribute("userId");
		return service.findComments(postId, userId, request);
	}


	@PostMapping("/{postId}/comments")
	@AccessTokenCheck
	public void createComment(
		@PathVariable Integer postId,
		@Valid @RequestBody CommentCreateRequest request,
		HttpServletRequest httpServletRequest
	) {
		Integer userId = (Integer) httpServletRequest.getAttribute("userId");
		service.create(postId, userId, request);
	}

	@PatchMapping("/comments/{commentId}")
	@AccessTokenCheck
	public void updateComment(
		@PathVariable Integer commentId,
		@Valid @RequestBody CommentUpdateRequest request,
		HttpServletRequest httpServletRequest
	){
		Integer userId = (Integer) httpServletRequest.getAttribute("userId");
		service.update(commentId, userId, request);
	}

	@DeleteMapping("/comments/{commentId}")
	@AccessTokenCheck
	public void deleteComment(
		@PathVariable Integer commentId,
		HttpServletRequest httpServletRequest
	){
		Integer userId = (Integer) httpServletRequest.getAttribute("userId");
		service.delete(commentId, userId);
	}
}
