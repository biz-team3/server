package com.bizteam3.server.post.service;

import com.bizteam3.server.global.exception.BusinessException;
import com.bizteam3.server.global.exception.ErrorCode;
import com.bizteam3.server.global.exception.common.BadRequestException;
import com.bizteam3.server.global.exception.common.ForbiddenException;
import com.bizteam3.server.global.exception.common.NotFoundException;
import com.bizteam3.server.post.dao.MediaDao;
import com.bizteam3.server.post.dao.HashtagDao;
import com.bizteam3.server.post.dao.PostDao;
import com.bizteam3.server.post.dto.MediaReplaceRequest;
import com.bizteam3.server.post.dto.MediaRequest;
import com.bizteam3.server.post.dto.PostCreateRequest;
import com.bizteam3.server.post.dto.PostUpdateCaptionRequest;
import com.bizteam3.server.post.entity.Media;
import com.bizteam3.server.post.entity.Post;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	private final PostDao postDao;
	private final MediaDao mediaDao;
	private final HashtagDao hashtagDao;

	@Transactional
	public void createPost(PostCreateRequest request, Integer userId) {
		Post post = new Post(
			userId,
			request.getCaption(),
			request.getTranslatedCaption()
		);

		int insertedCheck = postDao.insert(post);

		if (insertedCheck > 0) {
			Set<String> hashtagSet = post.createHashtag();

			if (!hashtagSet.isEmpty()) {
				hashtagDao.insertHashtag(hashtagSet);
				hashtagDao.insertPostHashtag(post.getPostId(), hashtagSet);
			}
		}

		if (request.getMedia() != null) {
			for (MediaRequest mediaRequest : request.getMedia()) {
				Media media = new Media(
					post.getPostId(),
					mediaRequest.getMediaType(),
					mediaRequest.getMediaUrl(),
					mediaRequest.getSortOrder(),
					mediaRequest.getOriginalFileName()
				);
				mediaDao.insert(media);
			}
		}
	}

	@Transactional
	public void updateCaption(Integer postId, PostUpdateCaptionRequest request, Integer userId){
		Post post = new Post(
				postId,
				request.getCaption(),
				request.getTranslatedCaption(),
				userId
		);

		if(userId != postDao.selectUserId(postId)){
			throw new ForbiddenException(ErrorCode.FORBIDDEN, "게시물 작성자만 수정할 수 있습니다.") {
			};
		}

		postDao.updateCaption(post);
		hashtagDao.deletePostHashtagByPostId(postId);
		Set<String> hashtagSet = post.createHashtag();
		if (!hashtagSet.isEmpty()) {
			hashtagDao.insertHashtag(hashtagSet);
			hashtagDao.insertPostHashtag(post.getPostId(), hashtagSet);
		}
  }

  	@Transactional
	public void replaceMedia(Integer postId, MediaReplaceRequest request, Integer userId){
		if (!postDao.existsByPostId(postId)) {
			throw new NotFoundException(ErrorCode.NOT_FOUND, "게시물을 찾을 수 없습니다.");
		}

		if (userId != postDao.selectUserId(postId)) {
			throw new ForbiddenException(ErrorCode.FORBIDDEN, "게시물 작성자만 미디어를 수정할 수 있습니다.");
		}

		if (request.getMedia() == null || request.getMedia().isEmpty()) {
			throw new BadRequestException(ErrorCode.BAD_REQUEST, "미디어는 최소 1개 이상 필요합니다.");
		}

		mediaDao.deleteByPostId(postId);

		for (MediaRequest mediaRequest : request.getMedia()) {
			Media media = new Media(
					postId,
					mediaRequest.getMediaType(),
					mediaRequest.getMediaUrl(),
					mediaRequest.getSortOrder(),
					mediaRequest.getOriginalFileName()
			);
			mediaDao.insert(media);
		}
	}

	@Override
	public boolean deletePost(Integer postId) {
		postDao.deleteMediasByPostId(postId);
		postDao.deletePostHashtagsByPostId(postId);

		int rows = postDao.delete(postId);
		if(rows == 1) return true;
		else return false;
	}

}

