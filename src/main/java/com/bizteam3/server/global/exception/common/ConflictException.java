package com.bizteam3.server.global.exception.common;

import com.bizteam3.server.global.exception.BusinessException;
import com.bizteam3.server.global.exception.ErrorCode;

/**
 * 409 Conflict - 리소스 충돌이 발생한 경우 (중복 등)
 */
public class ConflictException extends BusinessException {

	public ConflictException() {
		super(ErrorCode.CONFLICT);
	}

	public ConflictException(ErrorCode errorCode) {
		super(errorCode);
	}

	public ConflictException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}

	public ConflictException(String message) {
		super(ErrorCode.CONFLICT, message);
	}

	@Override
	public boolean isNecessaryToLog() {
		return false;
	}
}

