package com.bizteam3.server.user.auth.service;

import com.bizteam3.server.global.auth.jwt.JwtService;
import com.bizteam3.server.global.exception.BusinessException;
import com.bizteam3.server.global.exception.ErrorCode;
import com.bizteam3.server.global.exception.common.UnauthorizedException;
import com.bizteam3.server.user.auth.dto.AuthUserResponse;
import com.bizteam3.server.user.auth.dto.GetMeResponse;
import com.bizteam3.server.user.auth.dto.LoginRequest;
import com.bizteam3.server.user.auth.dto.LoginResponse;
import com.bizteam3.server.user.dao.UserDao;
import com.bizteam3.server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserDao userDao;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        User user = userDao.findByUsername(request.getUsername());

        if (user == null) {
            throw new UnauthorizedException(
                    ErrorCode.NOT_FOUND,
                    "요청 값이 올바르지 않습니다."
            );
        }

        if (!user.getPassword().equals(request.getPassword())) {
            throw new UnauthorizedException(
                    ErrorCode.UNAUTHORIZED,
                    "사용자 이름 또는 비밀번호가 올바르지 않습니다."
            );
        }

        String accessToken = jwtService.createJwt(
                user.getUserId(),
                user.getUsername(),
                user.getName()
        );

        AuthUserResponse authUser = new AuthUserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getName()
        );


        return new LoginResponse(
                accessToken,
                "Bearer",
                authUser
        );
    }

    public GetMeResponse getMe(String accessToken) {
        Integer userId = jwtService.getUserId(accessToken);

        User user = userDao.selectMeById(userId);

        return new GetMeResponse(
                user.getUserId(),
                user.getUsername(),
                user.getName(),
                user.getProfileImg()
        );

    }

}
