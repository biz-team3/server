package com.bizteam3.server.post.dto;

import com.bizteam3.server.post.dao.row.FeedPostRow;
import com.bizteam3.server.post.dto.vo.Author;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class FeedPostResponse {
    private Integer postId;
    private Author author;
    private List<PostMediaResponse> media; //타입 추가
    private String caption;
    private String translatedCaption;
    private LocalDateTime createdAt;
    private Integer likeCount;
    private Integer commentCount;
    private Boolean likedByMe;
    private Boolean savedByMe;
    private Boolean isOwner;

    public FeedPostResponse(FeedPostRow row) { //매개변수를 통해 값 가져와야함*
        this.postId = row.getPostId();
        this.author = new Author(
                row.getUserId(),
                row.getUsername(),
                row.getProfileImageUrl()
        );
        this.media = new ArrayList<>();
        this.caption = row.getCaption();
        this.translatedCaption = row.getTranslatedCaption();
        this.createdAt = row.getCreatedAt();
        this.likeCount = row.getLikeCount();
        this.commentCount = row.getCommentCount();
        this.likedByMe = row.getLikedByMe();
        this.savedByMe = row.getSavedByMe();
        this.isOwner = row.getIsOwner();
    }
}
