package com.bizteam3.server.post.service;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.follows.dao.FollowDao;
import com.bizteam3.server.global.exception.BusinessException;
import com.bizteam3.server.global.exception.ErrorCode;
import com.bizteam3.server.global.exception.common.BadRequestException;
import com.bizteam3.server.global.exception.common.ForbiddenException;
import com.bizteam3.server.global.exception.common.NotFoundException;
import com.bizteam3.server.post.dao.*;
import com.bizteam3.server.post.dao.row.FeedPostMediaRow;
import com.bizteam3.server.post.dao.row.FeedPostRow;
import com.bizteam3.server.post.dto.*;
import com.bizteam3.server.post.entity.Media;
import com.bizteam3.server.post.entity.Post;

import com.bizteam3.server.save.dao.SaveDao;
import com.bizteam3.server.user.dao.UserDao;
import com.bizteam3.server.user.entity.AccountVisType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	private final PostDao postDao;
	private final MediaDao mediaDao;
	private final HashtagDao hashtagDao;
	private final LikeDao likeDao;;
	private final CommentDao commentDao;
	private final SaveDao saveDao;;
	private final FollowDao followDao;

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

	@Override
	@Transactional(readOnly = true)
	public PageResponse<FeedPostResponse> getFeedPosts(PageRequest requset, Integer userId) {
		List<FeedPostRow> rows = postDao.selectFeedPosts(
				userId,
				requset.getOffset(),
				requset.getSize()
		);

		int total = postDao.countFeedPosts(userId);

		List<FeedPostResponse> responses = rows.stream()
				.map(FeedPostResponse::new)
				.toList();

		if (responses.isEmpty()) {
			return new PageResponse<>(responses, requset, total);
		}

		List<Integer> postIds = responses.stream()
				.map(FeedPostResponse::getPostId)
				.toList();

		List<FeedPostMediaRow> mediaRows = mediaDao.selectByPostIds(postIds);

		Map<Integer, List<PostMediaResponse>> mediaMap = mediaRows.stream()
				.collect(Collectors.groupingBy(
						FeedPostMediaRow::getPostId,
						Collectors.mapping(
								row -> new PostMediaResponse(
										row.getMediaId(),
										row.getMediaType(),
										row.getMediaUrl(),
										row.getSortOrder()
								),
								Collectors.toList()
						)
				));

		for (FeedPostResponse response : responses) {
			response.setMedia(
					mediaMap.getOrDefault(response.getPostId(), List.of())
			);
		}

		return new PageResponse<>(responses, requset, total);
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

	public PostDetailResponse getPostDetail(Integer postId, Integer userId) {
		PostDetailRow response = postDao.selectDetailByPostId(postId);

		if (response == null) {
			throw new NotFoundException(ErrorCode.NOT_FOUND, "게시물을 찾을 수 없습니다.");
		}

		List<FeedPostMediaRow> mediaRows = mediaDao.selectByPostIds(List.of(postId));
		List<PostMediaResponse> media = mediaRows.stream()
				.map(row -> new PostMediaResponse(
						row.getMediaId(),
						row.getMediaType(),
						row.getMediaUrl(),
						row.getSortOrder()
				))
				.toList();


		boolean likedByMe = likeDao.isLiked(userId, postId) > 0;
		boolean savedByMe = saveDao.isSaved(userId, postId) > 0;
		boolean isOwner = userId.equals(response.getAuthorUserId());
		boolean isFollowing = followDao.countByUsers(userId, response.getAuthorUserId()) > 0;
		boolean isPrivate = response.getAuthorAccountVis() == AccountVisType.PRIVATE;

		if (isPrivate && !isOwner && !isFollowing) {
			throw new ForbiddenException(ErrorCode.FORBIDDEN, "게시물을 볼 수 없습니다.");
		}

        return new PostDetailResponse(
                response.getPostId(),
                new PostAuthorResponse(
                        response.getAuthorUserId(),
                        response.getAuthorUsername(),
                        response.getAuthorProfileImageUrl()
                ),
                media,
                response.getCaption(),
                response.getTranslatedCaption(),
                response.getCreatedAt(),
                likeDao.countByPostId(postId),
                commentDao.countAllByPostId(postId),
                likedByMe,
                savedByMe,
                isOwner
        );
	}

}

