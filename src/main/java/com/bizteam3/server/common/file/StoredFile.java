package com.bizteam3.server.common.file;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoredFile {
	private String originalFileName;
	private String storedFileName;
	private String imageUrl;
	private String contentType;
	private long fileSize;
}
