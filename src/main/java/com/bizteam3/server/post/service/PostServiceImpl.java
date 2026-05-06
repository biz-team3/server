package com.bizteam3.server.post.service;

import org.springframework.stereotype.Service;

import com.bizteam3.server.author.dao.AuthorDao;
import com.bizteam3.server.author.entity.Author;
import com.bizteam3.server.global.exception.common.NotFoundException;
import com.bizteam3.server.post.dao.PostDao;
import com.bizteam3.server.post.dto.CreatePostRequest;
import com.bizteam3.server.post.dto.CreatePostResponse;
import com.bizteam3.server.post.entity.Post;

@Service
public class PostServiceImpl implements PostService{
	private final PostDao postDao;
	private final AuthorDao authorDao;

	public PostServiceImpl(PostDao postDao, AuthorDao authorDao) {
		this.postDao = postDao;
		this.authorDao = authorDao;
	}

	@Override
	public CreatePostResponse createPost(CreatePostRequest request ,Integer authorId) {
		Post post = new Post(
			authorId,
			request.toMediaList(),
			request.getCaption(),
			request.getTranslatedCaption(),
			request.getHashtags()
		);

		int insert = postDao.insert(post);

		if(insert == 0)
			throw new NotFoundException();

		Author author = authorDao.findById(authorId);

		return CreatePostResponse.toDto(post, author);
	}
}
