package com.will.constant

/**
 * Created by zoumy on 2017/5/11 10:56.
 */

/**
 * Created by Will on 2016/8/18 11:24.
 * 自定义异常码枚举
 *
 * 100起表示 controller层异常
 * 200起表示 service层异常
 * 300起表示 dao数据库交互层异常
 * 400起表示 其他异常
 *
 */
enum class ExceptionCode (code:Int,message:String){

    CONTROLLER_RETURN_ERROR(101, "正确的返回值被用于错误返回"),
    SERVICE_EXCEPTION(201, "service 运行异常"),
    SQL_EXECUTE_EXCEPTION(301, "sql执行异常"),
    ERROR_SQL_PARAM(302, "错误的sql参数"),
    ERROR_ID_CARD_LENGTH(401, "身份证号长度不合法");


    /**
     * 异常码
     */
    val code: Int = code
    /**
     * 描述
     */
    val message: String = message

    override fun toString(): String = "code:$code,message:$message"
}