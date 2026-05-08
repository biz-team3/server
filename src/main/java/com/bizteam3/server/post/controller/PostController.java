package com.bizteam3.server.post.controller;

import com.bizteam3.server.post.dto.PostCreateRequest;
import com.bizteam3.server.post.service.PostService;
import org.springframework.web.bind.annotation.*;

@RestController
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

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Integer postId){
        boolean result = postService.deletePost(postId);
        if(result){
            return "삭제 성공";
        }else{
            return "삭제 실패";
        }
    }
}
