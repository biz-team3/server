package com.bizteam3.server.post.dto;

import java.util.List;
import java.util.stream.IntStream;

import com.bizteam3.server.common.file.StoredFile;
import com.bizteam3.server.post.dto.vo.UploadedMedia;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MediaUploadResponse {
	private List<UploadedMedia> media;

	public static MediaUploadResponse toDto(List<StoredFile> storedFiles) {
		List<UploadedMedia> media = IntStream.range(0, storedFiles.size())
			.mapToObj(i -> UploadedMedia.toDto(storedFiles.get(i), i))
			.toList();
		return new MediaUploadResponse(media);
	}
}
