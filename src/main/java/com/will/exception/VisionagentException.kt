package com.will.exception

import com.will.constant.ExceptionCode
import com.will.utils.Value

/**
 * Created by zoumy on 2017/5/11 10:59.
 */
class VisionagentException : RuntimeException {
    val code:ExceptionCode

    constructor(code: ExceptionCode) : super() {
        this.code = code
    }

    constructor(message: String?, code: ExceptionCode) : super(message) {
        this.code = code
    }

    constructor(message: String?, cause: Throwable?, code: ExceptionCode) : super(message, cause) {
        this.code = code
    }

    constructor(cause: Throwable?, code: ExceptionCode) : super(cause) {
        this.code = code
    }

    constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean, code: ExceptionCode) : super(message, cause, enableSuppression, writableStackTrace) {
        this.code = code
    }


    override fun toString(): String {
        return "错误码：$code \n ${Value.ofEmpty(localizedMessage)}"
    }
}