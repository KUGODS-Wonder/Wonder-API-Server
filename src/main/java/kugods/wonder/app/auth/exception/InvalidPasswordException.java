package kugods.wonder.app.auth.exception;

import kugods.wonder.app.common.constant.ErrorCode;
import kugods.wonder.app.common.exception.GeneralException;

public class InvalidPasswordException extends GeneralException {
    public InvalidPasswordException() {
        super(ErrorCode.NOT_VALID_PASSWORD);
    }
}
