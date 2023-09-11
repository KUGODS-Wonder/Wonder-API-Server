package kugods.wonder.app.common.exception;

import kugods.wonder.app.common.constant.ErrorCode;

public class NotAvailableRedissonLockException extends GeneralException {
    public NotAvailableRedissonLockException() {
        super(ErrorCode.NOT_AVAILABLE_REDISSON_LOCK);
    }
}
