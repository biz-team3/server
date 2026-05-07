package com.bizteam3.server.user.service;

import java.util.List;

import com.bizteam3.server.global.exception.common.DatabaseException;
import com.bizteam3.server.global.exception.common.BadRequestException;
import com.bizteam3.server.global.exception.common.ConflictException;
import com.bizteam3.server.global.exception.common.NotFoundException;
import com.bizteam3.server.user.dao.UserDao;
import com.bizteam3.server.user.dto.UserCreateRequest;
import com.bizteam3.server.user.dto.UserListResponse;
import com.bizteam3.server.user.dto.UserResponse;
import com.bizteam3.server.user.dto.UserUpdateRequest;
import com.bizteam3.server.user.entity.AccountVisType;
import com.bizteam3.server.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public UserResponse create(UserCreateRequest request){
        validateAccountVisibility(request.getAccountVisibility());
        ensureUsernameAvailable(request.getUsername(), null);

        User user = UserCreateRequest.toEntity(request);
        int insert = userDao.insert(user);
        if (insert != 1){
            throw new DatabaseException("회원가입에 실패하였습니다");
        }
        return UserResponse.from(userDao.findByUsername(user.getUsername()));
    }

    public UserListResponse findAll(String query, int page, int size) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 100);
        int offset = safePage * safeSize;
        String normalizedQuery = query == null ? "" : query.trim();
        long totalElements = userDao.countAll(normalizedQuery);
        List<UserResponse> users = userDao.findAll(normalizedQuery, offset, safeSize)
                .stream()
                .map(UserResponse::from)
                .toList();
        int totalPages = totalElements == 0 ? 0 : (int) Math.ceil((double) totalElements / safeSize);
        return new UserListResponse(users, safePage, safeSize, totalElements, totalPages, safePage + 1 < totalPages);
    }

    public UserResponse findById(Integer userId) {
        return UserResponse.from(findUserById(userId));
    }

    public UserResponse findByUsername(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw NotFoundException.of("User", username);
        }
        return UserResponse.from(user);
    }

    public UserResponse update(Integer userId, UserUpdateRequest request) {
        User user = findUserById(userId);
        String nextUsername = defaultString(request.getUsername(), user.getUsername()).trim();
        validateAccountVisibility(request.getAccountVisibility());
        ensureUsernameAvailable(nextUsername, userId);

        user.setUsername(nextUsername);
        user.setName(defaultString(request.getName(), user.getName()));
        user.setBio(defaultString(request.getBio(), user.getBio()));
        user.setWebsite(defaultString(request.getWebsite(), user.getWebsite()));
        String profileImageUrl = request.resolveProfileImageUrl();
        if (profileImageUrl != null) {
            user.setProfileImg(profileImageUrl);
        }
        if (request.getAccountVisibility() != null && !request.getAccountVisibility().isBlank()) {
            user.setAccountVis(AccountVisType.valueOf(request.getAccountVisibility()));
        }

        int update = userDao.update(user);
        if (update != 1) {
            throw new DatabaseException("사용자 수정에 실패하였습니다");
        }
        return UserResponse.from(findUserById(userId));
    }

    public UserResponse delete(Integer userId) {
        User user = findUserById(userId);
        int delete = userDao.softDelete(userId);
        if (delete != 1) {
            throw new DatabaseException("사용자 삭제에 실패하였습니다");
        }
        return UserResponse.from(user);
    }

    private User findUserById(Integer userId) {
        User user = userDao.findById(userId);
        if (user == null) {
            throw NotFoundException.of("User", userId);
        }
        return user;
    }

    private void ensureUsernameAvailable(String username, Integer currentUserId) {
        if (username == null || username.isBlank()) {
            throw new BadRequestException("username은 필수입니다.");
        }
        User existing = userDao.findByUsername(username);
        if (existing != null && !existing.getUserId().equals(currentUserId)) {
            throw new ConflictException("이미 사용 중인 사용자 이름입니다.");
        }
    }

    private void validateAccountVisibility(String accountVisibility) {
        if (accountVisibility == null || accountVisibility.isBlank()) {
            return;
        }
        try {
            AccountVisType.valueOf(accountVisibility);
        } catch (IllegalArgumentException error) {
            throw new BadRequestException("accountVisibility는 PUBLIC 또는 PRIVATE만 가능합니다.");
        }
    }

    private String defaultString(String value, String fallback) {
        return value == null ? fallback : value;
    }
}
