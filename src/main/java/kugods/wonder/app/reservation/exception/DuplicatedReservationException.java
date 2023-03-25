package kugods.wonder.app.reservation.exception;

import kugods.wonder.app.common.constant.ErrorCode;
import kugods.wonder.app.common.exception.GeneralException;

public class DuplicatedReservationException extends GeneralException {
    public DuplicatedReservationException() {
        super(ErrorCode.DuPLICATED_RESERVATION);
    }
}
