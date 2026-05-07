package com.bizteam3.server.user.controller;

import java.net.URI;

import com.bizteam3.server.user.dto.UserCreateRequest;
import com.bizteam3.server.user.dto.UserListResponse;
import com.bizteam3.server.user.dto.UserResponse;
import com.bizteam3.server.user.dto.UserUpdateRequest;
import com.bizteam3.server.user.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest request){
        UserResponse response = userService.create(request);
        return ResponseEntity
                .created(URI.create("/api/users/" + response.userId()))
                .body(response);
    }

    @GetMapping("")
    public UserListResponse getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(name = "q", defaultValue = "") String query
    ) {
        return userService.findAll(query, page, size);
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable Integer userId) {
        return userService.findById(userId);
    }

    @GetMapping("/by-username/{username}")
    public UserResponse getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @PatchMapping("/{userId}")
    public UserResponse updateUser(@PathVariable Integer userId, @RequestBody UserUpdateRequest request) {
        return userService.update(userId, request);
    }

    @DeleteMapping("/{userId}")
    public UserResponse deleteUser(@PathVariable Integer userId) {
        return userService.delete(userId);
    }

}
