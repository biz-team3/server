package com.bizteam3.server.global.exception.common;

import com.bizteam3.server.global.exception.BusinessException;
import com.bizteam3.server.global.exception.ErrorCode;

/**
 * 400 Bad Request - 잘못된 요청인 경우
 */
public class BadRequestException extends BusinessException {

	public BadRequestException() {
		super(ErrorCode.BAD_REQUEST);
	}

	public BadRequestException(ErrorCode errorCode) {
		super(errorCode);
	}

	public BadRequestException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}

	public BadRequestException(String message) {
		super(ErrorCode.BAD_REQUEST, message);
	}

	@Override
	public boolean isNecessaryToLog() {
		return false;
	}
}
