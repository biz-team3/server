package com.bizteam3.server.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	BAD_REQUEST(
		HttpStatus.BAD_REQUEST,
		"400",
		"잘못된 요청입니다."
	),

	UNAUTHORIZED(
		HttpStatus.UNAUTHORIZED,
		"401",
		"인증이 필요합니다."
	),

	FORBIDDEN(
		HttpStatus.FORBIDDEN,
		"403",
		"접근 권한이 없습니다."
	),

	NOT_FOUND(
		HttpStatus.NOT_FOUND,
		"404",
		"요청한 리소스를 찾을 수 없습니다."
	),

	CONFLICT(
		HttpStatus.CONFLICT,
		"409",
		"이미 존재하거나 충돌이 발생한 요청입니다."
	),

	INTERNAL_ERROR(
		HttpStatus.INTERNAL_SERVER_ERROR,
		"500",
		"서버 오류가 발생했습니다."
	),
	
		INVALID_PARAMETER(
		HttpStatus.BAD_REQUEST,
		"500",
		"잘못된 파라미터 형식입니다."
	),

	DATABASE_ERROR(
		HttpStatus.INTERNAL_SERVER_ERROR,
		"500",
		"데이터베이스 오류가 발생했습니다."
	);

	private final HttpStatus httpStatus;
	private final String code;
	private final String detail;
}
