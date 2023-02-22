package kugods.wonder.app.member.exception;

import kugods.wonder.app.common.constant.ErrorCode;
import kugods.wonder.app.common.exception.GeneralException;

public class InvalidTokenException extends GeneralException {
    public InvalidTokenException() {
        super(ErrorCode.NOT_VALID_TOKEN);
    }
}
