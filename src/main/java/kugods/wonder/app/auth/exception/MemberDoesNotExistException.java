package kugods.wonder.app.auth.exception;

import kugods.wonder.app.common.constant.ErrorCode;
import kugods.wonder.app.common.exception.GeneralException;

public class MemberDoesNotExistException extends GeneralException {
    public MemberDoesNotExistException() {
        super(ErrorCode.MEMBER_DOES_NOT_EXIST);
    }
}
