package com.bizteam3.server.post.controller;

import com.bizteam3.server.post.dto.PostCreateRequest;
import com.bizteam3.server.post.entity.Post;
import com.bizteam3.server.post.service.PostService;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        postService.CreatePost(request, userId);

        return "등록완료";
    }

}
