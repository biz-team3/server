package com.bizteam3.server.global.exception;

import java.net.URI;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

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

	/**
	 * [Validation] @RequestBody @Valid 검증 실패
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail handle(MethodArgumentNotValidException ex) {
		ErrorCode errorCode = ErrorCode.BAD_REQUEST;

		log.warn("[ValidationException] code={}, message={}", errorCode.getCode(), ex.getMessage());

		String message = ex.getBindingResult()
			.getFieldErrors()
			.stream()
			.findFirst()
			.map(error -> error.getField() + ": " + error.getDefaultMessage())
			.orElse("요청 값 검증에 실패했습니다.");

		return createProblemDetail(
			errorCode.getHttpStatus(),
			errorCode.getCode(),
			message,
			ex.getClass().getSimpleName()
		);
	}


	/**
	 * [Exception] 지원하지 않는 Media Type
	 */
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ProblemDetail handle(HttpMediaTypeNotSupportedException ex) {
		String message = String.format("지원하지 않는 Content-Type입니다: %s", ex.getContentType());
		log.warn("[MediaTypeNotSupportedException] {}", message);

		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		problemDetail.setType(URI.create(ERROR_DOC_URI));
		problemDetail.setTitle("UNSUPPORTED_MEDIA_TYPE");
		problemDetail.setDetail(message);
		problemDetail.setProperty("exception", ex.getClass().getSimpleName());
		problemDetail.setProperty("timestamp", LocalDateTime.now());

		return problemDetail;
	}

	/**
	 * [Exception] 예측하지 못한 모든 예외 처리
	 */
	@ExceptionHandler(Exception.class)
	public ProblemDetail handle(Exception ex) {
		log.error("[UnhandledException] ", ex);

		return createProblemDetail(
			HttpStatus.INTERNAL_SERVER_ERROR,
			ErrorCode.INTERNAL_ERROR.getCode(),
			ErrorCode.INTERNAL_ERROR.getDetail(),
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
}
