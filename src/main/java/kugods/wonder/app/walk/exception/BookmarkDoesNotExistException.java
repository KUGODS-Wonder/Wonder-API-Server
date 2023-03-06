package kugods.wonder.app.walk.exception;

import kugods.wonder.app.common.constant.ErrorCode;
import kugods.wonder.app.common.exception.GeneralException;

public class BookmarkDoesNotExistException extends GeneralException {
    public BookmarkDoesNotExistException() {
        super(ErrorCode.BOOKMARK_DOES_NOT_EXIST);
    }
}
