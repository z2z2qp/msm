package com.will.framework

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializeFilter
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter
import org.springframework.http.HttpOutputMessage

/**
 * Created by zoumy on 2017/5/12 16:38.
 */
class FastJsonWithFilterHttpMessageConverter : FastJsonHttpMessageConverter() {
    var filter:SerializeFilter? = null

    override fun writeInternal(obj: Any?, outputMessage: HttpOutputMessage?) {
        val out = outputMessage!!.body
        var text:String?
        if(this.filter != null){
            text = JSON.toJSONString(obj,filter,*fastJsonConfig.serializerFeatures)
        }else{
            text = JSON.toJSONString(obj, *fastJsonConfig.serializerFeatures)
        }
        var i = text.indexOf("\"{\"")
        var i2 = text.indexOf("\"}\"")
        var str = text.substring(0,i)+text.substring(i+1,i2+2)+text.substring(i2+3)
        var bytes = str.toByteArray(fastJsonConfig.charset)
        out.write(bytes)
    }
}