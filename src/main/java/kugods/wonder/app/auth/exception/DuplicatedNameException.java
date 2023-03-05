package kugods.wonder.app.auth.exception;

import kugods.wonder.app.common.constant.ErrorCode;
import kugods.wonder.app.common.exception.GeneralException;

public class DuplicatedNameException extends GeneralException {
    public DuplicatedNameException() {
        super(ErrorCode.DUPLICATED_NAME);
    }
}
