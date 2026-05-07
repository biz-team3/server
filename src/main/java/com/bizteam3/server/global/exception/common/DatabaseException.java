package com.bizteam3.server.global.exception.common;

import com.bizteam3.server.global.exception.BusinessException;
import com.bizteam3.server.global.exception.ErrorCode;

public class DatabaseException extends BusinessException {
    public DatabaseException() {
        super(ErrorCode.DATABASE_ERROR);
    }

    public DatabaseException(String message) {
        super(ErrorCode.DATABASE_ERROR, message);
    }

    public DatabaseException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DatabaseException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    @Override
    public boolean isNecessaryToLog() {
        return false;
    }
}
