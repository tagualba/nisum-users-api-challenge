package com.nisum.users.statics;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    INVALID_DATA(1001, "Invalid data input", HttpStatus.BAD_REQUEST),
    REPEAT_EMAIL(1002, "Repeat Email", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1003, "Invalid Token Access", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN(1004, "Token Expired ", HttpStatus.UNAUTHORIZED),
    COMBINATION_FAIL(1005, "Invalid Combination", HttpStatus.NOT_FOUND);

    private final Integer code;

    private final String message;

    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public static ErrorCode get(String name) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.name().equals(name)) {
                return errorCode;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getName() {
        return this.name();
    }
}
