package com.will.framework.interceptor

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import com.will.constant.Constant
import com.will.constant.ControllerCode
import com.will.framework.controller.Result
import com.will.framework.entity.LoginLock
import com.will.framework.service.LoginLockService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

/**
 * Created by zoumy on 2017/5/11 10:36.
 */
class LoginInterceptor :HandlerInterceptorAdapter(){

    @Autowired
    var loginLockService:LoginLockService? = null

    val log:Logger = LoggerFactory.getLogger(LoginInterceptor::class.java)

    override fun preHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?): Boolean {
        val session = request!!.session
        val code = request.getParameter(Constant.VERIFY_CODE)//获取验证码
        val userName = request.getParameter(Constant.USER_NAME)
        var result = false
        if (checkVerifyCode(session, code)) {//校验验证码
            val ll = loginLockService!!.getLoginLockByLoginName(userName)
            if (isLocked(ll)) {//用户是否被锁
                val str = JSON.toJSONString(Result(ControllerCode.USER_LOCKED, ll.lockTime!!), SerializerFeature.WriteEnumUsingToString)
                response!!.writer.print(str)
            } else {
                result = true
            }
        } else {
            val str = JSON.toJSONString(Result(ControllerCode.INVALID_VERIFY_CODE), SerializerFeature.WriteEnumUsingToString)
            response!!.writer.print(str)
        }
        return result
    }

    private fun checkVerifyCode(session:HttpSession,code:String):Boolean{
        val verifyCode = session.getAttribute(Constant.VERIFY_CODE) as String
        log.debug("verifyCode,code:{},{}", verifyCode, code)
        return verifyCode.equals(code, ignoreCase = true)
    }

    private fun isLocked(loginLock: LoginLock): Boolean {
        return loginLock.loginCount!! >= 5 && loginLock.lockTime!! > System.currentTimeMillis()
    }
}