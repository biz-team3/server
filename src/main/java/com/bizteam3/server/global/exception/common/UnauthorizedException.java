package com.bizteam3.server.global.exception.common;

import com.bizteam3.server.global.exception.BusinessException;
import com.bizteam3.server.global.exception.ErrorCode;

/**
 * 401 Unauthorized - 인증이 필요한 경우
 */
public class UnauthorizedException extends BusinessException {

	public UnauthorizedException() {
		super(ErrorCode.UNAUTHORIZED);
	}

	public UnauthorizedException(ErrorCode errorCode) {
		super(errorCode);
	}

	public UnauthorizedException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}

	public UnauthorizedException(String message) {
		super(ErrorCode.UNAUTHORIZED, message);
	}

	@Override
	public boolean isNecessaryToLog() {
		return false;
	}
}
