package com.bizteam3.server.profile.service;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.profile.dto.ContentResponse;

public interface ProfileService {
	PageResponse<ContentResponse> getPosts(Integer userId, PageRequest pageRequest);
}
