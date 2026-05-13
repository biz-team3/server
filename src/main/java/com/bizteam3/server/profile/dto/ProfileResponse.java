package com.bizteam3.server.profile.dto;

import com.bizteam3.server.follows.dto.FollowViewerRelation;
import com.bizteam3.server.user.entity.AccountVisType;
import com.bizteam3.server.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponse {
    Integer userId;
    String username;
    String name;
    String bio;
    String website;
    String profileImageUrl;
    Integer followerCount;
    Integer followingCount;
    Integer postCount;
    AccountVisType accountVisibility;
    FollowViewerRelation viewerRelation;
    Boolean canViewContent;
    Boolean isOwner;

    public static ProfileResponse fromMe(
            User user,
            Integer followerCount,
            Integer followingCount,
            Integer postCount
    ) {
        return ProfileResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .name(user.getName())
                .bio(user.getBio())
                .website(user.getWebsite())
                .profileImageUrl(user.getProfileImg())
                .followerCount(followerCount)
                .followingCount(followingCount)
                .postCount(postCount)
                .accountVisibility(user.getAccountVis())
                .viewerRelation(FollowViewerRelation.SELF)
                .canViewContent(true)
                .isOwner(true)
                .build();
    }

    public static ProfileResponse fromUser(
            User user,
            Integer followerCount,
            Integer followingCount,
            Integer postCount,
            FollowViewerRelation viewerRelation,
            Boolean canViewContent
    ) {
        return ProfileResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .name(user.getName())
                .bio(user.getBio())
                .website(user.getWebsite())
                .profileImageUrl(user.getProfileImg())
                .followerCount(followerCount)
                .followingCount(followingCount)
                .postCount(postCount)
                .accountVisibility(user.getAccountVis())
                .viewerRelation(viewerRelation)
                .canViewContent(canViewContent)
                .isOwner(viewerRelation == FollowViewerRelation.SELF)
                .build();
    }

}
