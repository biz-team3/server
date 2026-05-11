package com.bizteam3.server.user.controller;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.user.dto.UploadUserProfileResponse;
import com.bizteam3.server.user.dto.UserCreateRequest;
import com.bizteam3.server.user.dto.UserResponse;
import com.bizteam3.server.user.dto.UserUpdateRequest;
import com.bizteam3.server.user.entity.User;
import com.bizteam3.server.user.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(@RequestBody UserCreateRequest request) {
        userService.create(request);
        return "회원가입 성공";
    }

    @PostMapping("/media/profile-images")
    public UploadUserProfileResponse uploadUserProfile(
        @RequestPart("file") MultipartFile file
    ){
        return userService.uploadUserProfile(file);
    }

    @GetMapping
    public PageResponse<UserResponse> getUsers(@Valid PageRequest pageRequest){
        return userService.findUsers(pageRequest);
    }

    @PatchMapping("/{userId}")
    public String modifyUser(@PathVariable Integer userId, @RequestBody UserUpdateRequest request) {
        User user = new User();
        user.setUserId(userId);
        user.setUsername(request.getUsername());
        if(request.getPassword() != null && !request.getPassword().isEmpty()){
            user.setPassword(request.getPassword());
        }
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
    public String removeUser(@PathVariable String userId) {
        userService.remove(userId);
        return "삭제 성공";
    }
}
