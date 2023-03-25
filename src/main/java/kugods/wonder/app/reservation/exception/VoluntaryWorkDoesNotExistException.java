package kugods.wonder.app.reservation.exception;

import kugods.wonder.app.common.constant.ErrorCode;
import kugods.wonder.app.common.exception.GeneralException;

public class VoluntaryWorkDoesNotExistException extends GeneralException {
    public VoluntaryWorkDoesNotExistException() {
        super(ErrorCode.VOLUNTARY_WORK_NOT_EXIST);
    }
}
