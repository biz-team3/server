package com.bizteam3.server.global.exception;

import java.net.URI;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalException {

	private static final String ERROR_DOC_URI = "/docs/index.html#error-code-list";

	/**
	 * [Exception] 비즈니스/도메인 예외
	 */
	@ExceptionHandler(BusinessException.class)
	public ProblemDetail handle(BusinessException ex) {
		ErrorCode errorCode = ex.getErrorCode();

		if (ex.isNecessaryToLog()) {
			log.error("[BusinessException] code={}, message={}", errorCode.getCode(), ex.getMessage(), ex);
		} else {
			log.warn("[BusinessException] code={}, message={}", errorCode.getCode(), ex.getMessage());
		}

		return createProblemDetail(
			errorCode.getHttpStatus(),
			errorCode.getCode(),
			ex.getMessage(),
			ex.getClass().getSimpleName()
		);
	}

	private ProblemDetail createProblemDetail(HttpStatus status, String code, String detail, String exceptionName) {
		ProblemDetail problemDetail = ProblemDetail.forStatus(status);
		problemDetail.setType(URI.create(ERROR_DOC_URI));
		problemDetail.setTitle(code);
		problemDetail.setDetail(detail);
		problemDetail.setProperty("exception", exceptionName);
		problemDetail.setProperty("timestamp", LocalDateTime.now());
		return problemDetail;
	}

	/**
	 * 필드 에러 상세 정보
	 */
	public record FieldErrorDetail(
		String field,
		Object rejectedValue,
		String message
	) {
	}
}
