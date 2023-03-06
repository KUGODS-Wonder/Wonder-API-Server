package kugods.wonder.app.record.exception;

import kugods.wonder.app.common.constant.ErrorCode;
import kugods.wonder.app.common.exception.GeneralException;

public class DuplicatedBookmarkException extends GeneralException {
    public DuplicatedBookmarkException() {
        super(ErrorCode.DUPLICATED_BOOKMARK);
    }
}
