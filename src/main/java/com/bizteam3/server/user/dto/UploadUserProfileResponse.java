package com.bizteam3.server.user.dto;

import com.bizteam3.server.common.file.StoredFile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadUserProfileResponse {
	String imageUrl;
	String originalFileName;
	String contentType;

	public static UploadUserProfileResponse toDto(StoredFile profiles) {
		return new UploadUserProfileResponse(
			profiles.getImageUrl(),
			profiles.getOriginalFileName(),
			profiles.getContentType()
		);
	}
}
