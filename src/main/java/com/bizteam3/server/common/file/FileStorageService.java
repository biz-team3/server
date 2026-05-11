package com.bizteam3.server.common.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bizteam3.server.global.exception.common.DatabaseException;
import com.bizteam3.server.global.exception.common.InvalidParameterException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileStorageService {

	private final FileUploadProperties properties;

	public StoredFile store(MultipartFile file, String dirName) {
		validate(file);

		try {
			LocalDate now = LocalDate.now();

			String datePath = now.getYear() + "/" + String.format("%02d", now.getMonthValue());

			Path uploadDir = Paths.get(properties.getRootDir())
				.toAbsolutePath()
				.normalize()
				.resolve(dirName)
				.resolve(datePath);

			Files.createDirectories(uploadDir);

			String originalFileName = file.getOriginalFilename();
			String extension = getExtension(originalFileName);
			String storedFileName = UUID.randomUUID() + extension;

			Path targetPath = uploadDir.resolve(storedFileName).normalize();

			if (!targetPath.startsWith(uploadDir)) {
				throw new InvalidParameterException("잘못된 파일 경로입니다.");
			}

			file.transferTo(targetPath.toFile());

			String fileUrl = properties.getUrlPrefix()
				+ "/" + dirName
				+ "/" + datePath
				+ "/" + storedFileName;

			return StoredFile.builder()
				.originalFileName(originalFileName)
				.storedFileName(storedFileName)
				.imageUrl(fileUrl)
				.contentType(file.getContentType())
				.fileSize(file.getSize())
				.build();

		} catch (IOException e) {
			throw new DatabaseException("파일 저장에 실패했습니다.");
		}
	}

	public List<StoredFile> storeAll(List<MultipartFile> files, String dirName) {
		return files.stream()
			.map(file -> store(file, dirName))
			.toList();
	}

	private void validate(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new InvalidParameterException("파일이 비어있습니다.");
		}

		String contentType = file.getContentType();

		if (contentType == null || !contentType.startsWith("image/")) {
			throw new InvalidParameterException("이미지 파일만 업로드할 수 있습니다.");
		}

		long maxSize = 10 * 1024 * 1024;

		if (file.getSize() > maxSize) {
			throw new InvalidParameterException("파일 크기는 10MB를 넘을 수 없습니다.");
		}
	}

	private String getExtension(String fileName) {
		if (fileName == null || !fileName.contains(".")) {
			return "";
		}

		return fileName.substring(fileName.lastIndexOf("."));
	}
}
