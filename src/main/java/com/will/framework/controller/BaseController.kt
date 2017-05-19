package com.will.framework.controller

import com.will.constant.ControllerCode
import com.will.constant.ExceptionCode
import com.will.exception.VisionagentException
import org.springframework.web.bind.annotation.ModelAttribute
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

/**
 * Created by zoumy on 2017/5/11 13:04.
 */
open  class BaseController {

    var request: HttpServletRequest? = null
    var response: HttpServletResponse? = null
    var session:HttpSession? = null

    @ModelAttribute
    private fun setRRS(request: HttpServletRequest, response: HttpServletResponse) {
        this.request = request
        this.response = response
        this.session = request.session
    }


    /**
     * 构造正确返回
     * @param data
     * *
     * @return
     */
    fun resultOK(data: Any): Result {
        return Result(ControllerCode.OK, data)
    }

    /**
     * 构造错误返回
     * @param code
     * *
     * @return
     */
    fun resultError(code: ControllerCode): Result {
        if (ControllerCode.OK == code) {
            throw VisionagentException(ExceptionCode.CONTROLLER_RETURN_ERROR)
        }
        return Result(code)
    }

    /**
     * 构造错误返回
     * @param code
     * *
     * @return
     */
    fun resultError(code: ControllerCode, desc: Any): Result {
        if (ControllerCode.OK == code) {
            throw VisionagentException(ExceptionCode.CONTROLLER_RETURN_ERROR)
        }
        return Result(code, desc)
    }
}