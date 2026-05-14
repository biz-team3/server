package com.bizteam3.server.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizteam3.server.common.dto.PageRequest;
import com.bizteam3.server.common.dto.PageResponse;
import com.bizteam3.server.global.exception.common.DatabaseException;
import com.bizteam3.server.notification.dao.NotificationDao;
import com.bizteam3.server.notification.entity.Notification;
import com.bizteam3.server.notification.entity.NotificationType;
import com.bizteam3.server.post.dao.CommentDao;
import com.bizteam3.server.post.dao.PostDao;
import com.bizteam3.server.post.dao.row.CommentListRow;
import com.bizteam3.server.post.dto.CommentCreateRequest;
import com.bizteam3.server.post.dto.CommentResponse;
import com.bizteam3.server.post.dto.CommentUpdateRequest;
import com.bizteam3.server.post.entity.Comment;
import com.bizteam3.server.user.dao.UserDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentDao commentDao;
	private final PostDao postDao;
	private final NotificationDao notificationDao;
	private final UserDao userDao;

	@Override
	@Transactional
	public void create(Integer postId, Integer userId, CommentCreateRequest request) {
		Comment comment = CommentCreateRequest.toEntity(postId, userId, request);
		int insert = commentDao.insert(comment);
		if (insert != 1)
			throw new DatabaseException("댓글 저장에 실패했습니다.");

		Integer postOwnerId = postDao.selectUserId(postId);
		if (!userId.equals(postOwnerId)) {
			notificationDao.insert(new Notification(
				postOwnerId,
				userId,
				NotificationType.COMMENT,
				"POST",
				postId,
				"게시물에 댓글을 남겼습니다."
			));
		}
	}

	@Override
	public void update(Integer commentId, CommentUpdateRequest request) {
		int update = commentDao.update(CommentUpdateRequest.toEntity(commentId, request));
		if (update != 1)
			throw new DatabaseException("존재하지 않는 commentId입니다. [삭제 실패]");
	}

	@Override
	public void delete(Integer commentId) {
		int delete = commentDao.delete(commentId);
		if (delete != 1)
			throw new DatabaseException("존재하지 않는 commentId입니다 [삭제 실패].");
	}

	@Override
	@Transactional(readOnly = true)
	public PageResponse<CommentResponse> findComments(
		Integer postId,
		Integer userId,
		PageRequest request
	) {
		List<CommentListRow> comments = commentDao.selectPage(
			postId,
			request.getOffset(),
			request.getSize()
		);

		List<CommentResponse> responses = comments.stream()
			.map(comment -> CommentResponse.toDto(comment, userId))
			.toList();

		int total = commentDao.countAllByPostId(postId);

		return new PageResponse<>(responses, request, total);
	}
}
