package kugods.wonder.app.reservation.exception;

import kugods.wonder.app.common.constant.ErrorCode;
import kugods.wonder.app.common.exception.GeneralException;

public class ReservationDoesNotExistException extends GeneralException {
    public ReservationDoesNotExistException() {
        super(ErrorCode.RESERVATION_NOT_EXIST);
    }
}
