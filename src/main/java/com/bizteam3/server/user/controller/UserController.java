package com.bizteam3.server.user.controller;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.user.dto.UserCreateRequest;
import com.bizteam3.server.user.dto.UserResponse;
import com.bizteam3.server.user.dto.UserUpdateRequest;
import com.bizteam3.server.user.entity.User;
import com.bizteam3.server.user.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public String createUser(@RequestBody UserCreateRequest request) {
        userService.create(request);
        return "회원가입 성공";
    }

    @GetMapping
    public PageResponse<UserResponse> getUsers(@Valid PageRequest pageRequest){
        return userService.findUsers(pageRequest);
    }

    @PatchMapping("/{userId}")
    public String modifyUser(@PathVariable("userId") Integer userId, @RequestBody UserUpdateRequest request) {
        //TODO: jwt로 변환 필요
        // 클라이언트가 보낸 JSON 데이터를 엔티티로 변환하여 받음
        User user = new User();
        user.setUserId(userId);
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setBio(request.getBio());
        user.setWebsite(request.getWebsite());
        user.setProfileImg(request.resolveProfileImg());
        user.setAccountVis(request.resolveAccountVis());
        user.setUpdateAt(request.getUpdateAt());

        userService.modify(user);

        return "업데이트 성공";
    }

    @DeleteMapping("/{userId}")
    public String removeUser(@PathVariable("userId") String userId) {
        //TODO: jwt로 변환 필요
        userService.remove(userId);
        return "삭제 성공";
    }
}
