package com.bizteam3.server.post.service;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.post.dto.*;

public interface PostService {
    void createPost(PostCreateRequest request, Integer userId);

    PageResponse<FeedPostResponse> getFeedPosts(PageRequest requset, Integer userId);

    void updateCaption(Integer postId, PostUpdateCaptionRequest request, Integer userId);
    void replaceMedia(Integer postId, MediaReplaceRequest request, Integer userId);
    boolean deletePost(Integer postId);
    PostDetailResponse getPostDetail(Integer postId, Integer userId);
}
