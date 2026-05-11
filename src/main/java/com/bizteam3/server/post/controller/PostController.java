package com.bizteam3.server.post.controller;

import com.bizteam3.server.post.dto.MediaReplaceRequest;
import java.util.List;

import com.bizteam3.server.post.dto.MediaUploadResponse;
import com.bizteam3.server.post.dto.PostCreateRequest;
import com.bizteam3.server.post.dto.PostUpdateCaptionRequest;
import com.bizteam3.server.post.service.LikeService;
import com.bizteam3.server.post.service.MediaService;
import com.bizteam3.server.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public String createPost(@RequestBody PostCreateRequest request){
        //TODO: AUth 필수
        Integer userId = 1;
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
    public String updatePost(
            @PathVariable Integer postId,
            @RequestBody PostUpdateCaptionRequest request){
        Integer userId = 1;
        //TODO: 테스트용 postId, 추후에는 pathVariable로 받아야함.
        Integer testPostId = 1;
        postService.updateCaption(testPostId, request, userId);

        return "수정완료";
    }

    @PutMapping("/{postId}/media")
    public String replaceMedia(
            @PathVariable Integer postId,
            @RequestBody MediaReplaceRequest request){
        Integer userId = 1;
        postService.replaceMedia(postId, request, userId);
        return "미디어 교체 완료";
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Integer postId){
        boolean result = postService.deletePost(postId);
        if(result){
            return "삭제 성공";
        }else{
            return "삭제 실패";
        }
    }

    @PostMapping("/{postId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void likePost(@PathVariable Integer postId){
        Integer userId = 1;
        //TODO: 테스트용 postId, 추후에는 pathVariable로 받아야함.
        Integer testPostId = 1;
        likeService.likePost(userId, testPostId);
    }

    @DeleteMapping("/{postId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlikePost(@PathVariable Integer postId){
        Integer userId = 1;
        //TODO: 테스트용 postId, 추후에는 pathVariable로 받아야함.
        Integer testPostId = 1;
        likeService.unlikePost(userId, testPostId);
    }
}
