package com.bizteam3.server.user.controller;

import com.bizteam3.server.user.dto.UserCreateRequest;
import com.bizteam3.server.user.dto.UserRemoveResponse;
import com.bizteam3.server.user.dto.UserUpdateRequest;
import com.bizteam3.server.user.entity.User;
import com.bizteam3.server.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 회원가입
    @PostMapping("")
    public String createUser(@RequestBody UserCreateRequest request) {
        userService.create(request);
        return "회원가입 성공";
    }

    // 조회 - 광재
//    @GetMapping("")

    // 수정
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

        // 수정된 후 DB에 저장된 회원 정보를 다시 조회하여 반환
        userService.modify(user);

        return "업데이트 성공"; // @RestController가 자동으로 JSON 직렬화? (수업내용)
    }

    // 삭제
    @DeleteMapping("/{userId}")
    public UserRemoveResponse removeUser(@PathVariable("userId") String userId) {
        //TODO: jwt로 변환 필요
        boolean result = userService.remove(userId);

        UserRemoveResponse response = new UserRemoveResponse();
        if (result) {
            response.setResult("success");
            response.setMessage("회원 탈퇴 성공. 해당 회원 정보는 더 이상 존재하지 않습니다.");
        } else {
            response.setResult("failure");
            response.setMessage("회원 탈퇴 실패. 해당 회원이 존재하지 않거나 삭제 과정에서 오류가 발생했습니다.");
        }
        return response; // @RestController가 자동으로 JSON 직렬화
    }
}
