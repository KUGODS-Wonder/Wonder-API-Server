package kugods.wonder.app.walk.exception;

import kugods.wonder.app.common.constant.ErrorCode;
import kugods.wonder.app.common.exception.GeneralException;

public class WalkDoesNotExistException extends GeneralException {
    public WalkDoesNotExistException() {
        super(ErrorCode.WALK_DOES_NOT_EXIST);
    }
}
