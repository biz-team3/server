package com.bizteam3.server.save.controller;

import com.bizteam3.server.save.service.SaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class SaveController {
    private final SaveService saveService;

    @PostMapping("/{postId}/save")
    public String savePost(@PathVariable Integer postId) {
        // TODO: JWT 적용 후 토큰에서 userId 추출
        Integer userId = 1;
        saveService.save(postId, userId);
        return "저장되었습니다.";
    }

    @DeleteMapping("/{postId}/save")
    public String unsavePost(@PathVariable Integer postId) {
        // TODO: JWT 적용 후 토큰에서 userId 추출
        Integer userId = 1;
        saveService.unsave(postId, userId);
        return "삭제되었습니다.";
    }
}
