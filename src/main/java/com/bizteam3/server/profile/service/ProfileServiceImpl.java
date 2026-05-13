package com.bizteam3.server.profile.service;

import com.bizteam3.server.follows.dao.FollowDao;
import com.bizteam3.server.follows.dao.FollowRequestDao;
import com.bizteam3.server.follows.dto.FollowViewerRelation;
import com.bizteam3.server.global.exception.common.NotFoundException;
import com.bizteam3.server.post.dao.PostDao;
import com.bizteam3.server.profile.dto.ProfileResponse;
import com.bizteam3.server.user.dao.UserDao;
import com.bizteam3.server.user.entity.AccountVisType;
import com.bizteam3.server.user.entity.User;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.post.dao.CommentDao;
import com.bizteam3.server.post.dao.LikeDao;
import com.bizteam3.server.post.dao.MediaDao;
import com.bizteam3.server.post.dao.PostDao;
import com.bizteam3.server.post.entity.Media;
import com.bizteam3.server.post.entity.Post;
import com.bizteam3.server.profile.dto.ContentResponse;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
	private final PostDao postDao;
	private final LikeDao likeDao;
	private final CommentDao commentDao;
	private final MediaDao mediaDao;
	private final UserDao userDao;
	private final FollowDao followDao;
	private final FollowRequestDao followRequestDao;

	@Override
    @Transactional
	public ProfileResponse myProfile (Integer userId){
        User user = userDao.selectById(userId);

        if(user == null || user.getDeleteAt() != null){//?
            throw NotFoundException.of("User", userId);
        }

		int followerCount = followDao.countFollowers(userId);
		int followingCount = followDao.countFollowing(userId);
		int postCount = postDao.countAllByUserId(userId);

		return ProfileResponse.fromMe(
				user,
				followerCount,
				followingCount,
				postCount
		);
    }
	@Override
	@Transactional(readOnly = true)
	public ProfileResponse getProfileByUserId(Integer userId, Integer viewerId) {
		User user = getActiveUser(userId);

		int followerCount = followDao.countFollowers(userId);
		int followingCount = followDao.countFollowing(userId);
		int postCount = postDao.countAllByUserId(userId);

		FollowViewerRelation viewerRelation = resolveViewerRelation(userId, viewerId);
		boolean canViewContent = user.getAccountVis() == AccountVisType.PUBLIC
				|| viewerRelation == FollowViewerRelation.SELF
				|| viewerRelation == FollowViewerRelation.FOLLOWING;

		return ProfileResponse.fromUser(
				user,
				followerCount,
				followingCount,
				postCount,
				viewerRelation,
				canViewContent
		);
	}

	@Override
	@Transactional(readOnly = true)
	public PageResponse<ContentResponse> getPosts(
			Integer userId,
			PageRequest request
	) {
		List<Post> posts = postDao.selectFeedPostsByUserId(
				userId,
				request.getOffset(),
				request.getSize());

		int total = postDao.countAllByUserId(userId);
		List<ContentResponse> list = getList(posts);

		return new PageResponse<>(list, request, total);
	}

	private User getActiveUser(Integer userId){
		User user = userDao.selectById(userId);
		if(user == null || user.getDeleteAt() != null){
			throw NotFoundException.of("User", userId);
		}
		return user;
	}

	private FollowViewerRelation resolveViewerRelation(Integer userId, Integer viewerId){
		if(userId.equals(viewerId)){
			return FollowViewerRelation.SELF;
		}

		if(followDao.countByUsers(viewerId, userId) > 0){
			return FollowViewerRelation.FOLLOWING;
		}

		if(followRequestDao.countPending(userId, userId) > 0){
			return FollowViewerRelation.PENDING;
		}

		return FollowViewerRelation.NOT_FOLLOWING;
	}

	private @NonNull List<ContentResponse> getList(List<Post> posts) {
		return posts.stream()
			.map(post -> {
				Integer postId = post.getPostId();

				int commentCount = commentDao.countAllByPostId(postId);
				int likeCount = likeDao.countAllByPostId(postId);
				int mediaCount = mediaDao.countAllByPostId(postId);
				Media firstByPostId = mediaDao.findFirstByPostId(postId);

				return ContentResponse.toDto(
					post.getPostId(),
					firstByPostId.getMediaUrl(),
					mediaCount,
					commentCount,
					likeCount
				);
			})
			.toList();
	}
}
