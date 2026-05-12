package com.bizteam3.server.profile.service;

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
public class ProfileServiceImpl implements ProfileService {
	private final PostDao postDao;
	private final LikeDao likeDao;
	private final CommentDao commentDao;
	private final MediaDao mediaDao;

	public ProfileServiceImpl(PostDao postDao, LikeDao likeDao, CommentDao commentDao, MediaDao mediaDao) {
		this.postDao = postDao;
		this.likeDao = likeDao;
		this.commentDao = commentDao;
		this.mediaDao = mediaDao;
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
