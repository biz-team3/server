package com.bizteam3.server.post.dto.vo;

import com.bizteam3.server.common.file.StoredFile;
import com.bizteam3.server.post.entity.MediaType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadedMedia {
	MediaType type;
	String url;
	Integer sortOrder;
	String originalFileName;
	String contentType;
	Integer fileSize;

	public static UploadedMedia toDto(StoredFile storedFile, int sortOrder) {
		return UploadedMedia.builder()
			.type(MediaType.IMAGE)
			.url(storedFile.getImageUrl())
			.sortOrder(sortOrder)
			.originalFileName(storedFile.getOriginalFileName())
			.contentType(storedFile.getContentType())
			.fileSize((int)storedFile.getFileSize())
			.build();
	}
}
