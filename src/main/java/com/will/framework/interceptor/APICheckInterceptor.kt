package com.will.framework.interceptor

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONWriter
import com.will.constant.ControllerCode
import com.will.framework.controller.Result
import com.will.utils.Value
import com.will.utils.scure.Md5Secure
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*

/**
 * Created by zoumy on 2017/5/11 12:47.
 *  签名校验
 */
class APICheckInterceptor : HandlerInterceptor {
    val log:Logger = LoggerFactory.getLogger(this.javaClass)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val param = request.parameterMap
        val md5 = Value.ofEmpty(param["sign"])//获取上传的前面
        val sign = getSign(param)//得到参数签名
        log.debug("md5:" + md5)
        log.debug("sign:" + sign)
        val result = sign.equals(md5, ignoreCase = true)
        if (!result) {
            val str = JSON.toJSONString(Result(ControllerCode.INVALID_SIGN), JSONWriter.Feature.WriteEnumUsingToString)
            response.writer.print(str)
        }
        return result
    }

    fun  getSign(param: Map<String, Array<String>>): String{
        val list = ArrayList<String>()
        for ((key, value) in param) {
            if ("sign".equals(key, ignoreCase = true)) {
                continue
            }
            list.add(key + "=" + Value.ofEmpty(value) + "&")
            log.debug("{}:{}", key, Value.ofEmpty(value))
        }
        val size = list.size
        val arrayToSort = list.toTypedArray()
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER)
        val sb = StringBuilder()
        for (i in 0..size - 1) {
            sb.append(arrayToSort[i])
        }
        var result = sb.toString()
        result = Md5Secure.encode(result)!!.uppercase()
        return result

    }
}
