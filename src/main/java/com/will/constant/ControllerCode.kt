package com.will.constant

/**
 * Created by zoumy on 2017/5/11 9:50.
 */
enum class ControllerCode(code:Int,message:String) {
    OK(1,""),
    INVALID_ACCOUNT(101,"用户名或密码错误"),
    INVALID_TOKEN(103,"无效的token"),
    INVALID_SIGN(104,"签名错误"),
    INVALID_VERIFY_CODE(105,"验证码错误"),
    USER_LOCKED(106,"用户被锁定");
    var code:Int? = code
    var message:String? = message

    override fun toString(): String {
        return "{\"code\":\"$code\",\"message\":\"$message\"}"
    }
}