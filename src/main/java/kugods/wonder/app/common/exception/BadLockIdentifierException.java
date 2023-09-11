package kugods.wonder.app.common.exception;

import kugods.wonder.app.common.constant.ErrorCode;

public class BadLockIdentifierException extends GeneralException {
    public BadLockIdentifierException() {
        super(ErrorCode.BAD_LOCK_IDENTIFIER);
    }
}
