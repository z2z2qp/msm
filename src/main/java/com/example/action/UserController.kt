package com.example.action

import com.example.entity.Student
import com.example.service.UserService
import com.will.constant.Constant
import com.will.constant.ControllerCode
import com.will.framework.controller.BaseController
import com.will.framework.controller.Result
import com.will.framework.entity.LoginLock
import com.will.framework.service.LoginLockService
import com.will.utils.Value
import com.will.utils.VerifyCodeUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import java.io.IOException
import java.io.OutputStream
import javax.annotation.Resource

/**
 * Created by zoumy on 2017/5/11 10:08.
 */
@RequestMapping("/user")
@Controller
class UserController : BaseController() {
    val log:Logger = LoggerFactory.getLogger(UserController::class.java)

    @Resource(name = "userService")
    var service:UserService? = null

    @Resource(name = "loginLockService")
    var llService:LoginLockService? = null

    @ResponseBody
    @RequestMapping(value = "/login", method = arrayOf(RequestMethod.GET))
    fun login(loginName:String,password:String):Result{
        var result:Result?
        val ll:LoginLock = llService!!.getLoginLockByLoginName(loginName)
        log.debug("loginName:{}",loginName)
        log.debug("password:{}",password)
        val user:Student? = service!!.checkUser(loginName,password)
        if(user == null){
            ll.loginCount = if ( ll.loginCount!! >= 5) 0 else ll.loginCount!! +1
            result = resultError(ControllerCode.INVALID_ACCOUNT,ll.loginCount!!)
        }else{
            ll.loginCount = 0
            result = resultOK(user)
        }
        llService!!.saveOrUpdate(ll)
        return result
    }


    @ResponseBody
    @RequestMapping(value = "/verifyCode", method = arrayOf(RequestMethod.GET))
    fun verifyCode(widthInt:Int?,heightInt:Int?,verifySizeInt:Int?) {
        try {
            val four:Int = 4
            val forty:Int = 40
            val hundred:Int = 100
            val width = Value.of(widthInt,hundred)
            val height = Value.of(heightInt, forty)
            val verifySize = Value.of(verifySizeInt, four)
            val os:OutputStream = response!!.outputStream
            val code:String = VerifyCodeUtils.outputVerifyImage(width!!, height!!, os, verifySize!!)
            log.debug("verify code : {}",code)
            session!!.setAttribute(Constant.VERIFY_CODE, code)
            os.flush()
        } catch (e:IOException) {
            log.error(e.message,e)
        }
    }

    fun isLocked(ll: LoginLock):Boolean {
        return ll.loginCount!! >= 5 && ll.lockTime!! > System.currentTimeMillis()
    }

    @ResponseBody
    @RequestMapping(value = "/check", method = arrayOf(RequestMethod.GET))
    fun check(token:String):Result {
        service!!.checkToken(token);
        return resultError(ControllerCode.INVALID_TOKEN);
    }


    @ResponseBody
    @RequestMapping(value = "/queryAll", method = arrayOf(RequestMethod.GET))
    fun queryAll(page:Int, rows:Int):Result {
        return resultOK(service!!.queryAllUser(page, rows))
    }

    @ResponseBody
    @RequestMapping(value = "/test", method = arrayOf(RequestMethod.POST))
    fun test(token:Array<String>):Result {
        log.info(Value.ofEmpty(token))
        return resultOK(token)
    }
}