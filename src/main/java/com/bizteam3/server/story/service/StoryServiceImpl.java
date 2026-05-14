package com.bizteam3.server.story.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.common.file.FileStorageService;
import com.bizteam3.server.common.file.StoredFile;
import com.bizteam3.server.global.exception.common.DatabaseException;
import com.bizteam3.server.story.dao.StoryDao;
import com.bizteam3.server.story.dao.row.StoryRow;
import com.bizteam3.server.story.dto.StoryResponse;
import com.bizteam3.server.story.dto.UserStoryResponse;
import com.bizteam3.server.story.entity.Story;
import com.bizteam3.server.user.dao.UserDao;
import com.bizteam3.server.user.entity.User;

@Service
public class StoryServiceImpl implements StoryService {
	private final FileStorageService storageService;
	private final StoryDao storyDao;
	private final UserDao userDao;

	public StoryServiceImpl(FileStorageService storageService, StoryDao storyDao, UserDao userDao) {
		this.storageService = storageService;
		this.storyDao = storyDao;
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
	public UserStoryResponse getFeed(Integer userId, Integer viewerId) {
		boolean isOwner = Objects.equals(userId, viewerId);
		User user = userDao.selectById(userId);

		return getUserStoryResponse(user, isOwner, viewerId);
	}

	@Override
	@Transactional(readOnly = true)
	public PageResponse<UserStoryResponse> getFeeds(Integer userId, PageRequest request) {
		List<User> users = storyDao.selectFeedStoryUsersByViewerId(
			userId,
			request.getOffset(),
			request.getSize()
		);

		List<UserStoryResponse> list = users.stream()
			.map(user ->
				getUserStoryResponse(user, false, userId))
			.toList();

		int total = storyDao.countFeedStoryUsersByViewerId(userId);

		return new PageResponse<>(list, request, total);
	}

	private UserStoryResponse getUserStoryResponse(User user, boolean isOwner, Integer viewerId) {
		List<StoryRow> stories = storyDao.selectStoriesByUserId(user.getUserId(), viewerId);

		return UserStoryResponse.toDto(
			user.getUserId(),
			user.getUsername(),
			user.getProfileImg(),
			isOwner,
			StoryResponse.toListDto(stories)
		);
	}
}
