package com.bizteam3.server.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	INVALID_INPUT(HttpStatus.BAD_REQUEST, "500", "잘못된 요청 형식입니다."),
	INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "500", "잘못된 파라미터 형식입니다."),
	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "404", "요청한 리소스를 찾을 수 없습니다."),
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String detail;
}
