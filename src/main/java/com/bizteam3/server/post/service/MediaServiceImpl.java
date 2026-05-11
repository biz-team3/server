package com.bizteam3.server.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bizteam3.server.common.file.FileStorageService;
import com.bizteam3.server.common.file.StoredFile;
import com.bizteam3.server.post.dto.MediaUploadResponse;

@Service
public class MediaServiceImpl implements MediaService{
	private final FileStorageService storageService;

	public MediaServiceImpl(FileStorageService storageService) {
		this.storageService = storageService;
	}

	@Override
	public MediaUploadResponse uploadPostImages(List<MultipartFile> files) {
		List<StoredFile> storedFiles = storageService.storeAll(files, "posts");
		return MediaUploadResponse.toDto(storedFiles);
	}
}
