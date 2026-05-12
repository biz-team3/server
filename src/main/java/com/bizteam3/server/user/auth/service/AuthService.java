package com.bizteam3.server.user.auth.service;

import com.bizteam3.server.user.auth.dto.GetMeResponse;
import com.bizteam3.server.user.auth.dto.LoginRequest;
import com.bizteam3.server.user.auth.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    GetMeResponse getMe(String accessToken);
}
