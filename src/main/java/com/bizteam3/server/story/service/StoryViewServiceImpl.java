package com.bizteam3.server.story.service;

import org.springframework.stereotype.Service;

import com.bizteam3.server.story.dao.StoryViewDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoryViewServiceImpl implements StoryViewService{

	private final StoryViewDao dao;

	@Override
	public void see(Integer userId, Integer storyId) {
		dao.insert(userId, storyId);
	}
}
