package com.bizteam3.server.user.service;

import org.springframework.web.multipart.MultipartFile;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.user.dto.UploadUserProfileResponse;
import com.bizteam3.server.user.dto.UserCreateRequest;
import com.bizteam3.server.user.dto.UserResponse;
import com.bizteam3.server.user.entity.User;

public interface UserService {

	void create(UserCreateRequest request);

	UploadUserProfileResponse uploadUserProfile(MultipartFile file);

	PageResponse<UserResponse> findUsers(PageRequest pageRequest);

	void modify(User user);

	void remove(String mid);
}
