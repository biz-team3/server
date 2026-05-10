package com.bizteam3.server.post.controller;

import com.bizteam3.server.post.dto.PostCreateRequest;
import com.bizteam3.server.post.dto.PostUpdateCaptionRequest;
import com.bizteam3.server.post.service.LikeService;
import com.bizteam3.server.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final LikeService likeService;

    public PostController(PostService postService, LikeService likeService) {
        this.postService = postService;
        this.likeService = likeService;
    }

    @PostMapping
    public String createPost(@RequestBody PostCreateRequest request){
        //TODO: AUth 필수
        Integer userId = 1;
        postService.createPost(request, userId);

        return "등록완료";
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
