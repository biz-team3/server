package com.bizteam3.server.post.controller;

import com.bizteam3.server.post.dto.PostCreateRequest;
import com.bizteam3.server.post.dto.PostUpdateCaptionRequest;
import com.bizteam3.server.post.entity.Post;
import com.bizteam3.server.post.service.PostService;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.web.bind.annotation.*;

@RestController
//@XSlf4j
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
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


}
