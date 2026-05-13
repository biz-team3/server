package com.bizteam3.server.profile.service;

import com.bizteam3.server.profile.dto.ProfileRequest;
import com.bizteam3.server.profile.dto.ProfileResponse;
import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.profile.dto.ContentResponse;

public interface ProfileService {
    ProfileResponse myProfile (Integer userId);
    ProfileResponse getProfileByUserId(Integer userId, Integer viewerId);
    ProfileResponse getProfileByUsername(String username, Integer viewerId);
    ProfileResponse updateProfile(Integer userId, Integer viewerId, ProfileRequest request);
    PageResponse<ContentResponse> getPosts(Integer userId, PageRequest pageRequest);
}
