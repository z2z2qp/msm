package com.will.exception;

import com.will.constant.ExceptionCode;

/**
 * Created by Will on 2016/8/17 9:11.
 */
public class VisionagentException extends RuntimeException {

    private ExceptionCode code;

    public VisionagentException(ExceptionCode code) {
        this.code = code;
    }

    public VisionagentException(ExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    public VisionagentException(ExceptionCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public VisionagentException(ExceptionCode code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public VisionagentException(ExceptionCode code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    @Override
    public String toString() {
        String str = "错误码：" + code + " " + getLocalizedMessage();
        return str;
    }
}
