package com.wechat.servlet

import com.will.utils.HexByteUtils
import com.will.utils.PropertyLoader
import com.will.utils.Value
import com.will.utils.xml.XMLObject
import com.will.utils.xml.XMLUtils
import com.wechat.model.WXMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by zoumy on 2017/5/12 9:46.
 */
class CoreServlet : HttpServlet() {

    val log:Logger = LoggerFactory.getLogger(CoreServlet::class.java)

    companion object{
        val ask = PropertyLoader.getPropertiesFromClassPath("ask.properties")
        val key = PropertyLoader.getPropertiesFromClassPath("key.properties")
    }

    override fun doGet(request: HttpServletRequest?, response: HttpServletResponse?) {
        val signature = request!!.getParameter("signature")
        val timestamp = request.getParameter("timestamp")
        val nonce = request.getParameter("nonce")
        val echostr = request.getParameter("echostr")
        val out = response!!.writer
        if (checkSignature(signature, timestamp, nonce)) {
            out.print(echostr)
        }
        out.close()
    }

    private fun checkSignature(signature:String,timestamp:String,nonce:String):Boolean{
        val arr:Array<String> = arrayOf(key.getProperty("token"),timestamp,nonce)
        Arrays.sort(arr)
        val content:StringBuilder = StringBuilder()
        arr.forEach {
            content.append(it)
        }
        var md:MessageDigest
        var tmpStr:String? = null

        try {
            md = MessageDigest.getInstance("SHA-1")
            var digest: ByteArray? = md.digest(content.toString().toByteArray())
            tmpStr = HexByteUtils.byteArrayToHexString(digest)
        }catch (e:NoSuchAlgorithmException){
            log.error("",e)
        }
        return tmpStr != null && tmpStr.equals(signature,false)
    }

    override fun doPost(request: HttpServletRequest?, response: HttpServletResponse?) {
        response!!.characterEncoding = "UTF-8"
        val writer = response.writer

        val sb = StringBuilder()
        try {
            val reader = request!!.reader
            reader.forEachLine {
                sb.append(it)
            }
            val xmlObject = XMLUtils.xml2XMLObject(sb.toString())
            log.info("接收到的消息:{}",xmlObject.toString())

            val enevt:XMLObject = xmlObject.recursionValue("Event") as XMLObject
            val eventKey:XMLObject = xmlObject.recursionValue("EventKey") as XMLObject
            val toUserName:XMLObject = xmlObject.recursionValue("ToUserName") as XMLObject
            val fromUserName:XMLObject = xmlObject.recursionValue("FromUserName") as XMLObject
            val content:XMLObject = xmlObject.recursionValue("Content") as XMLObject
            val msgType:XMLObject = xmlObject.recursionValue("MsgType") as XMLObject

            var returnStr:String? = null
            if (checkValue(enevt,WXMessage.EventType.CLICK)){
                if(eventKey.getValue() != null){
                    returnStr = ask.getProperty(Value.ofEmpty(eventKey.getValue()))
                }
            }else if (checkValue(enevt,WXMessage.EventType.SUBSCRIBE)){
                returnStr = ask.getProperty("welcome")
            }else if(checkValue(msgType,WXMessage.MessageType.TEXT)){
                var o = ask.get(Value.ofEmpty(content.getValue()))
                if(o != null){
                    returnStr = o.toString()
                }else{
                    returnStr = ask.getProperty("error")
                }
            }
            if(returnStr.isNullOrBlank()){
                writer.print("success")
            }else{
                var message = WXMessage(Value.ofEmpty(fromUserName.getValue()),Value.ofEmpty(toUserName.getValue()),System.currentTimeMillis() / 1000,WXMessage.MessageType.TEXT,returnStr!!)
                log.info("发送消息{}", message.toString())
                writer.print(message.toString())
            }

        }catch (e:Exception) {
            log.error(e.message, e)
            writer.print("success")
        }
    }

    private fun checkValue(var1: XMLObject?, var2: String?): Boolean {
        return var1 != null && var2 != null && var2.equals(Value.ofEmpty(var1.getValue()), ignoreCase = true)
    }
}
