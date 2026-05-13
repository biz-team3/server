package com.bizteam3.server.profile.service;

import com.bizteam3.server.follows.dao.FollowDao;
import com.bizteam3.server.follows.dao.FollowRequestDao;
import com.bizteam3.server.follows.dto.FollowViewerRelation;
import com.bizteam3.server.global.exception.common.ConflictException;
import com.bizteam3.server.global.exception.common.DatabaseException;
import com.bizteam3.server.global.exception.common.ForbiddenException;
import com.bizteam3.server.global.exception.common.NotFoundException;
import com.bizteam3.server.post.dao.PostDao;
import com.bizteam3.server.profile.dto.ProfileRequest;
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
    @Transactional(readOnly = true)
	public ProfileResponse myProfile (Integer userId){
        User user = getActiveUser(userId);
		return buildProfileResponse(user, FollowViewerRelation.SELF, true);
    }

	@Override
	@Transactional(readOnly = true)
	public ProfileResponse getProfileByUserId(Integer userId, Integer viewerId) {
		User user = getActiveUser(userId);
		FollowViewerRelation viewerRelation = resolveViewerRelation(userId, viewerId);
		boolean canViewContent = canViewContent(user, viewerRelation);

		return buildProfileResponse(user, viewerRelation, canViewContent);
	}

	@Override
	@Transactional(readOnly = true)
	public ProfileResponse getProfileByUsername(String username, Integer viewerId){
		User user = userDao.findByUsername(username);

		if(user == null || user.getDeleteAt() != null){
			throw NotFoundException.of("User", username);
		}

		FollowViewerRelation viewerRelation = resolveViewerRelation(user.getUserId(), viewerId);
		boolean canViewContent = canViewContent(user, viewerRelation);

		return buildProfileResponse(user, viewerRelation, canViewContent);
	}

	@Override
	@Transactional
	public ProfileResponse updateProfile(
			Integer userId,
			Integer viewerId,
			ProfileRequest request
	){
		if (!userId.equals(viewerId)) {
			throw new ForbiddenException("본인 프로필만 수정할 수 있습니다.");
		}

		User currentUser = getActiveUser(userId);
		if (request.getUsername() != null
				&& !request.getUsername().isBlank()
				&& !request.getUsername().equals(currentUser.getUsername())) {
			User duplicatedUser = userDao.findByUsername(request.getUsername());

			if (duplicatedUser != null) {
				throw new ConflictException("이미 사용 중인 username입니다.");
			}
		}

		User user = request.toEntity(userId);

		int rows = userDao.update(user);

		if (rows != 1) {
			throw new DatabaseException("프로필 수정에 실패했습니다.");
		}

		return myProfile(userId);
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

	private ProfileResponse buildProfileResponse(
			User user,
			FollowViewerRelation viewerRelation,
			boolean canViewContent
	) {
		Integer userId = user.getUserId();

		int followerCount = followDao.countFollowers(userId);
		int followingCount = followDao.countFollowing(userId);
		int postCount = postDao.countAllByUserId(userId);

		return ProfileResponse.fromUser(
				user,
				followerCount,
				followingCount,
				postCount,
				viewerRelation,
				canViewContent
		);
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

		if(followRequestDao.countPending(viewerId, userId) > 0){
			return FollowViewerRelation.PENDING;
		}

		return FollowViewerRelation.NOT_FOLLOWING;
	}

	private boolean canViewContent(User user, FollowViewerRelation viewerRelation) {
		return user.getAccountVis() == AccountVisType.PUBLIC
				|| viewerRelation == FollowViewerRelation.SELF
				|| viewerRelation == FollowViewerRelation.FOLLOWING;
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
