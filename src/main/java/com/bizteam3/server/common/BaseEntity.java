package com.bizteam3.server.common;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity {
	LocalDateTime createdAt;
	LocalDateTime updatedAt;
	LocalDateTime deletedAt;

	/**
	 * 소프트 삭제
	 */
	public void softDelete() {
		this.deletedAt = LocalDateTime.now();
	}

	/**
	 * 소프트 삭제된 엔터티를 복원
	 */
	public void restore() {
		this.deletedAt = null;
	}

	public boolean isDeleted() {
		return deletedAt != null;
	}
}
