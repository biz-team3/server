package com.bizteam3.server.profile.controller;

import com.bizteam3.server.profile.dto.ProfileResponse;
import com.bizteam3.server.profile.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    ProfileService profileService;

    @GetMapping("/me")
    public ProfileResponse myProfile(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        return profileService.myProfile(userId);
    }
}
