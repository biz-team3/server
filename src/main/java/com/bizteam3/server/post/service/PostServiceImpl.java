package com.bizteam3.server.post.service;

import com.bizteam3.server.post.dao.MediaDao;
import com.bizteam3.server.post.dao.HashtagDao;
import com.bizteam3.server.post.dao.PostDao;
import com.bizteam3.server.post.dto.MediaRequest;
import com.bizteam3.server.post.dto.PostCreateRequest;
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
}



