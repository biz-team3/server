package com.bizteam3.server.post.controller;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.global.auth.annotation.AccessTokenCheck;
import com.bizteam3.server.post.dto.*;
import com.bizteam3.server.post.service.LikeService;
import com.bizteam3.server.post.service.MediaService;
import com.bizteam3.server.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final LikeService likeService;
    private final MediaService mediaService;

    public PostController(PostService postService, LikeService likeService, MediaService mediaService) {
        this.postService = postService;
        this.likeService = likeService;
        this.mediaService = mediaService;
    }

    @PostMapping
    @AccessTokenCheck
    public String createPost(
            @RequestBody PostCreateRequest request,
            HttpServletRequest httpServletRequest
    ) {
        Integer userId = (Integer) httpServletRequest.getAttribute("userId");
        postService.createPost(request, userId);

        return "등록완료";
    }

    @PostMapping(value = "/media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MediaUploadResponse uploadPostImages(
            @RequestPart("files") List<MultipartFile> files
    ) {
        return mediaService.uploadPostImages(files);
    }

    @PatchMapping("/{postId}")
    @AccessTokenCheck
    public String updatePost(
            @PathVariable Integer postId,
            @RequestBody PostUpdateCaptionRequest request,
            HttpServletRequest httpServletRequest
    ) {
        Integer userId = (Integer) httpServletRequest.getAttribute("userId");
        postService.updateCaption(postId, request, userId);

        return "수정완료";
    }

    @PutMapping("/{postId}/media")
    @AccessTokenCheck
    public String replaceMedia(
            @PathVariable Integer postId,
            @RequestBody MediaReplaceRequest request,
            HttpServletRequest httpServletRequest
    ) {
        Integer userId = (Integer) httpServletRequest.getAttribute("userId");
        postService.replaceMedia(postId, request, userId);
        return "미디어 교체 완료";
    }

    @DeleteMapping("/{postId}")
    @AccessTokenCheck
    public String deletePost(
            @PathVariable Integer postId,
            HttpServletRequest httpServletRequest
    ) {
        Integer userId = (Integer) httpServletRequest.getAttribute("userId");
        boolean result = postService.deletePost(postId, userId);
        if (result) {
            return "삭제 성공";
        } else {
            return "삭제 실패";
        }
    }

    @PostMapping("/{postId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @AccessTokenCheck
    public void likePost(
            @PathVariable Integer postId,
            HttpServletRequest httpServletRequest
    ) {
        Integer userId = (Integer) httpServletRequest.getAttribute("userId");
        likeService.likePost(userId, postId);
    }

    @DeleteMapping("/{postId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @AccessTokenCheck
    public void unlikePost(
            @PathVariable Integer postId,
            HttpServletRequest httpServletRequest
    ) {
        Integer userId = (Integer) httpServletRequest.getAttribute("userId");
        likeService.unlikePost(userId, postId);
    }

    @GetMapping("/feed")
    @AccessTokenCheck
    public PageResponse<FeedPostResponse> getFeedPosts(
            @Valid PageRequest request,
            HttpServletRequest httpServletRequest
    ) {
        Integer userId = (Integer) httpServletRequest.getAttribute("userId");
        return postService.getFeedPosts(request, userId);
    }

    @GetMapping("/{postId}")
    @AccessTokenCheck
    public PostDetailResponse getPostDetail(
            @PathVariable Integer postId,
            HttpServletRequest httpServletRequest
    ) {
        Integer userId = (Integer) httpServletRequest.getAttribute("userId");
        return postService.getPostDetail(postId, userId);
    }
}
