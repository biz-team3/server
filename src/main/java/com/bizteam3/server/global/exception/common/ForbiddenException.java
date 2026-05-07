package com.bizteam3.server.global.exception.common;

import com.bizteam3.server.global.exception.BusinessException;
import com.bizteam3.server.global.exception.ErrorCode;

/**
 * 403 Forbidden - 접근 권한이 없는 경우
 */
public class ForbiddenException extends BusinessException {

	public ForbiddenException() {
		super(ErrorCode.FORBIDDEN);
	}

	public ForbiddenException(ErrorCode errorCode) {
		super(errorCode);
	}

	public ForbiddenException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}

	public ForbiddenException(String message) {
		super(ErrorCode.FORBIDDEN, message);
	}

	@Override
	public boolean isNecessaryToLog() {
		return false;
	}
}

