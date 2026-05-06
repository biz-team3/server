package com.bizteam3.server.global.exception;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {

	private final ErrorCode errorCode;
	private final Object[] args;

	protected BusinessException(ErrorCode errorCode) {
		super(errorCode.getDetail());
		this.errorCode = errorCode;
		this.args = new Object[]{};
	}

	protected BusinessException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.args = new Object[]{};
	}

	/**
	 * 이 예외를 로그로 남길지 여부
	 */
	public abstract boolean isNecessaryToLog();

	/**
	 * HTTP 상태 코드 반환
	 */
	public int getHttpStatus() {
		return errorCode.getHttpStatus().value();
	}

}

