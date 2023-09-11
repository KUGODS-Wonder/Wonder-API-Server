package kugods.wonder.app.reservation.exception;

import kugods.wonder.app.common.constant.ErrorCode;
import kugods.wonder.app.common.exception.GeneralException;

public class CurrentPeopleNumberIsZeroException extends GeneralException {
    public CurrentPeopleNumberIsZeroException() {
        super(ErrorCode.CURRENT_PEOPLE_NUMBER_IS_ZERO);
    }
}
