package com.bizteam3.server.save.controller;

import com.bizteam3.server.global.auth.annotation.AccessTokenCheck;
import com.bizteam3.server.save.service.SaveService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class SaveController {
    private final SaveService saveService;

    @PostMapping("/{postId}/save")
    @AccessTokenCheck
    public String savePost(
        @PathVariable Integer postId,
        HttpServletRequest httpServletRequest
    ) {
        Integer userId = (Integer) httpServletRequest.getAttribute("userId");
        saveService.save(postId, userId);
        return "저장되었습니다.";
    }

    @DeleteMapping("/{postId}/save")
    @AccessTokenCheck
    public String unsavePost(
        @PathVariable Integer postId,
        HttpServletRequest httpServletRequest
    ) {
        Integer userId = (Integer) httpServletRequest.getAttribute("userId");
        saveService.unsave(postId, userId);
        return "삭제되었습니다.";
    }
}
