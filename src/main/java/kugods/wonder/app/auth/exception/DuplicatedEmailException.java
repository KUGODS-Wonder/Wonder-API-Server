package kugods.wonder.app.auth.exception;

import kugods.wonder.app.common.constant.ErrorCode;
import kugods.wonder.app.common.exception.GeneralException;

public class DuplicatedEmailException extends GeneralException {
    public DuplicatedEmailException() {
        super(ErrorCode.DUPLICATED_EMAIL);
    }
}
