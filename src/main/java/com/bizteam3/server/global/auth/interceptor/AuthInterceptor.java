package com.bizteam3.server.global.auth.interceptor;

import com.bizteam3.server.global.auth.annotation.AccessTokenCheck;
import com.bizteam3.server.global.auth.jwt.JwtService;
import com.bizteam3.server.global.exception.ErrorCode;
import com.bizteam3.server.global.exception.common.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtService jwtService;

    private String extractToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization == null) {
            return null;
        }

        if (!authorization.startsWith("Bearer ")) {
            return null;
        }

        String token = authorization.substring(7).trim();

        if (token.isBlank()) {
            return null;
        }

        return token;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        AccessTokenCheck methodAnnotation =
                handlerMethod.getMethodAnnotation(AccessTokenCheck.class);
        AccessTokenCheck classAnnotation =
                handlerMethod.getBeanType().getAnnotation(AccessTokenCheck.class);
        if (methodAnnotation == null && classAnnotation == null) {
            return true;
        }

        String token = extractToken(request);
        if (token == null) {
            throw new UnauthorizedException(
                    ErrorCode.UNAUTHORIZED,
                    "로그인이 필요합니다."
            );
        }

        jwtService.validateJwt(token);

        request.setAttribute("userId", jwtService.getUserId(token));
        request.setAttribute("username", jwtService.username(token));
        request.setAttribute("name", jwtService.name(token));

        return true;
    }
}
