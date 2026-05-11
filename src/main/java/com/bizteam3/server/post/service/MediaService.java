package com.bizteam3.server.post.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bizteam3.server.post.dto.MediaUploadResponse;

public interface MediaService {
	MediaUploadResponse uploadPostImages(List<MultipartFile> files);
}
