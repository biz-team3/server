package com.bizteam3.server.user.auth.controller;

import com.bizteam3.server.global.auth.annotation.AccessTokenCheck;
import com.bizteam3.server.user.auth.dto.GetMeResponse;
import com.bizteam3.server.user.auth.dto.LoginRequest;
import com.bizteam3.server.user.auth.dto.LoginResponse;
import com.bizteam3.server.user.auth.service.AuthService;
import com.bizteam3.server.user.auth.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    public final AuthService authService;
    public final TokenBlacklistService tokenBlacklistService;

    public AuthController(AuthService authService, TokenBlacklistService tokenBlacklistService) {
        this.authService = authService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String token = authorization.substring(7).trim();
        tokenBlacklistService.logout(token);
    }

    @GetMapping("/me")
    @AccessTokenCheck
    @ResponseStatus(HttpStatus.OK)
    public GetMeResponse me(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String token = authorization.substring(7).trim();
        return authService.getMe(token);
    }
}
