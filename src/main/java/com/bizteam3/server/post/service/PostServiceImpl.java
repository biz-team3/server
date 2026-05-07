package com.bizteam3.server.post.service;

import com.bizteam3.server.post.dao.MediaDao;
import com.bizteam3.server.post.dao.HashtagDao;
import com.bizteam3.server.post.dao.PostDao;
import com.bizteam3.server.post.dto.MediaRequest;
import com.bizteam3.server.post.dto.PostCreateRequest;
import com.bizteam3.server.post.entity.Media;
import com.bizteam3.server.post.entity.Hashtag;
import com.bizteam3.server.post.entity.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PostServiceImpl implements PostService {
    private final PostDao postDao;
    private final MediaDao mediaDao;

    public PostServiceImpl(PostDao postDao, MediaDao mediaDao) {
        this.postDao = postDao;
        this.mediaDao = mediaDao;
    private final HashtagDao hashtagDao;

    public PostServiceImpl(PostDao postDao, HashtagDao hashtagDao) {
        this.postDao = postDao;
        this.hashtagDao = hashtagDao;
    }

    @Transactional
    @Override
    @Transactional
    public void CreatePost(PostCreateRequest request, Integer userId) {
        Post post = new Post(
                userId,
                request.getCaption(),
                request.getTranslatedCaption()
        );

        postDao.insert(post);

        Integer postId = post.getPostId();

        if(request.getMedia() != null){
            for(MediaRequest mediaRequest : request.getMedia()){
                Media media = new Media();
                media.setPostId(postId);
                media.setMediaType(mediaRequest.getMediaType());
                media.setMediaUrl(mediaRequest.getMediaUrl());
                media.setSortOrder(mediaRequest.getSortOrder());
                media.setOriginalFileName(mediaRequest.getOriginalFileName());

                mediaDao.insert(media);
        int insertedCheck = postDao.insert(post);

        if (insertedCheck > 0) {
            Set<String> hashtagSet = post.createHashtag();

            if (!hashtagSet.isEmpty()) {
                hashtagDao.insertHashtag(hashtagSet);
                hashtagDao.insertPostHashtag(post.getPostId(), hashtagSet);
            }
        }
    }

}
