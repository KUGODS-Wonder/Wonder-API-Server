package kugods.wonder.app.reservation.exception;

import kugods.wonder.app.common.constant.ErrorCode;
import kugods.wonder.app.common.exception.GeneralException;

public class ExceedMaxPeopleNumberException extends GeneralException {
    public ExceedMaxPeopleNumberException() {
        super(ErrorCode.EXCEED_MAX_PEOPLE_NUMBER);
    }
}
