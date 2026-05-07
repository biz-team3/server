package com.bizteam3.server.user.controller;

import com.bizteam3.server.user.dto.UserCreateRequest;
import com.bizteam3.server.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public String createUser(@RequestBody UserCreateRequest request){
        userService.create(request);
        return "회원가입 성공";
    }

}
