package com.bizteam3.server.profile.service;

import com.bizteam3.server.profile.dto.ProfileResponse;

public interface ProfileService {
    ProfileResponse myProfile (Integer userId);
}
