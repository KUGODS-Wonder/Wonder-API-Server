package kugods.wonder.app.auth.exception;

import kugods.wonder.app.common.constant.ErrorCode;
import kugods.wonder.app.common.exception.GeneralException;

public class InvalidGoogleToken extends GeneralException {
    public InvalidGoogleToken() {
        super(ErrorCode.INVALID_GOOGLE_TOKEN);
    }
}
