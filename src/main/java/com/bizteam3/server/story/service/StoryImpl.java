package com.bizteam3.server.story.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bizteam3.server.common.file.FileStorageService;
import com.bizteam3.server.common.file.StoredFile;
import com.bizteam3.server.follows.dao.FollowDao;
import com.bizteam3.server.follows.dto.FollowUserResponse;
import com.bizteam3.server.global.exception.common.DatabaseException;
import com.bizteam3.server.story.dao.StoryDao;
import com.bizteam3.server.story.dto.StoryGroupResponse;
import com.bizteam3.server.story.dto.StoryResponse;
import com.bizteam3.server.story.dto.UserStoryResponse;
import com.bizteam3.server.story.entity.Story;
import com.bizteam3.server.user.dao.UserDao;
import com.bizteam3.server.user.entity.User;

@Service
public class StoryImpl implements StoryService {
	private final FileStorageService storageService;
	private final StoryDao storyDao;
	private final FollowDao followDao;
	private final UserDao userDao;

	public StoryImpl(FileStorageService storageService, StoryDao storyDao, FollowDao followDao, UserDao userDao) {
		this.storageService = storageService;
		this.storyDao = storyDao;
		this.followDao = followDao;
		this.userDao = userDao;
	}

	@Override
	public void create(Integer userId, MultipartFile file) {
		StoredFile stories = storageService.store(file, "stories");

		int insert = storyDao.insert(new Story(
			userId,
			stories.getImageUrl()
		));

		if (insert != 1)
			throw new DatabaseException("스토리 생성에 실패하였습니다.");
	}

	@Override
	@Transactional
	public void delete(Integer userId, Integer storyId) {
		Story story = storyDao.selectByUserIdAndStoryId(userId, storyId);
		if (story == null)
			throw new DatabaseException("존재하지 않거나 권한이 없는 스토리입니다.");

		int deleted = storyDao.delete(userId, storyId);

		if (deleted != 1)
			throw new DatabaseException("스토리 삭제에 실패했습니다.");
	}

	@Override
	@Transactional(readOnly = true)
	public UserStoryResponse getFeed(Integer userId) {
		User user = userDao.selectById(userId);
		return getUserStoryResponse(user, true);
	}

	@Override
	@Transactional(readOnly = true)
	public StoryGroupResponse getFeeds(Integer userId) {
		//TODO: 페이징 형태로 변경 고민 - Feeds
		List<FollowUserResponse> followUserResponses = followDao.selectFollowing(userId, userId, 0, 20);

		return StoryGroupResponse.toDto(
			followUserResponses.stream()
				.map(response -> {
					User user = userDao.selectById(response.getUserId());
					return getUserStoryResponse(user, false);
				}).toList()
		);
	}

	private UserStoryResponse getUserStoryResponse(User user, boolean isOwner) {
		List<Story> stories = storyDao.selectByUserId(user.getUserId());
		return UserStoryResponse.toDto(
			user.getUserId(),
			user.getUsername(),
			user.getProfileImg(),
			isOwner,
			StoryResponse.toListDto(stories)
		);
	}
}
