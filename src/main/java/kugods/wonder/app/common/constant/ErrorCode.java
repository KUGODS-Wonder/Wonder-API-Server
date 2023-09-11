package kugods.wonder.app.common.constant;

import kugods.wonder.app.common.exception.GeneralException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    OK(0, HttpStatus.OK, "Ok"),

    BAD_REQUEST(10000, HttpStatus.BAD_REQUEST, "Bad request"),
    NOT_FOUND(10001, HttpStatus.NOT_FOUND, "Requested resource is not found"),
    VALIDATION_ERROR(10002, HttpStatus.BAD_REQUEST, "Validation error"),

    INTERNAL_ERROR(20000, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),
    DATA_ACCESS_ERROR(20001, HttpStatus.INTERNAL_SERVER_ERROR, "Data access error"),
    BAD_LOCK_IDENTIFIER(20002, HttpStatus.INTERNAL_SERVER_ERROR, "Bad lock identifier"),
    NOT_AVAILABLE_REDISSON_LOCK(20003, HttpStatus.INTERNAL_SERVER_ERROR, "Not available Redisson lock"),

    NOT_VALID_ROLE(1000, HttpStatus.BAD_REQUEST, "Invalid role"),
    NOT_VALID_TOKEN(1001, HttpStatus.BAD_REQUEST, "Invalid token"),
    NOT_VALID_PASSWORD(1002, HttpStatus.BAD_REQUEST, "Password does not match"),
    MEMBER_DOES_NOT_EXIST(1003, HttpStatus.BAD_REQUEST, "Member does not exist"),
    DUPLICATED_EMAIL(1004, HttpStatus.BAD_REQUEST, "Duplicated email"),
    DUPLICATED_NAME(1005, HttpStatus.BAD_REQUEST, "Duplicated name"),
    WALK_DOES_NOT_EXIST(1006, HttpStatus.BAD_REQUEST, "Walk does not exist"),
    DUPLICATED_BOOKMARK(1007, HttpStatus.BAD_REQUEST, "Duplicated bookmark"),
    BOOKMARK_DOES_NOT_EXIST(1008, HttpStatus.BAD_REQUEST, "Bookmark does not exist"),
    VOLUNTARY_WORK_NOT_EXIST(1009, HttpStatus.BAD_REQUEST, "Voluntary work does not exist"),
    DuPLICATED_RESERVATION(1010, HttpStatus.BAD_REQUEST, "Duplicated reservation"),
    RESERVATION_NOT_EXIST(1011, HttpStatus.BAD_REQUEST, "Reservation does not exits"),

    INVALID_GOOGLE_TOKEN(1100, HttpStatus.BAD_REQUEST, "Invalid Google Token"),
    EXCEED_MAX_PEOPLE_NUMBER(1101, HttpStatus.BAD_REQUEST, "Exceed max people number"),
    CURRENT_PEOPLE_NUMBER_IS_ZERO(1102, HttpStatus.BAD_REQUEST, "Current people number is zero")
    ;

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;


    public static ErrorCode valueOf(HttpStatus httpStatus) {
        if (httpStatus == null) {
            throw new GeneralException("HttpStatus is null.");
        }

        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getHttpStatus() == httpStatus)
                .findFirst()
                .orElseGet(() -> {
                    if (httpStatus.is4xxClientError()) {
                        return ErrorCode.BAD_REQUEST;
                    } else if (httpStatus.is5xxServerError()) {
                        return ErrorCode.INTERNAL_ERROR;
                    } else {
                        return ErrorCode.OK;
                    }
                });
    }

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }

}