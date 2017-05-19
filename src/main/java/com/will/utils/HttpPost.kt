package com.will.utils

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


/**
 * Created by zoumy on 2017/5/15 16:47.
 */
object HttpPost {

    /**
     * 以Post方式向web接口请求数据,编码为utf-8
     *
     * 超时时间5s,编码UTF8
     *
     * @param actionUrl
     * @param params
     * @return
     */

    fun post(actionUrl:String,param:Map<String,Any>,timeout:Int = 5000):String{
        if(actionUrl.isBlank()){
            throw IllegalArgumentException("action url : $actionUrl")
        }
        val BOUNDARY = java.util.UUID.randomUUID().toString()
        val PREFIX = "--"
        val LINEND = "\r\n"
        val MULTIPART_FROM_DATA = "multipart/form-data"
        val CHARSET = "UTF-8"

        val url = URL(actionUrl)

        val conn = url.openConnection() as HttpURLConnection
        conn.connectTimeout = timeout
        conn.readTimeout = timeout
        conn.doInput = true
        conn.doOutput = true
        conn.useCaches = false
        conn.requestMethod = "POST"
        conn.setRequestProperty("connection", "keep-alive")
        conn.setRequestProperty("Charsert", "UTF-8")
        conn.setRequestProperty("Content-Type", "$MULTIPART_FROM_DATA;boundary=$BOUNDARY")

        val sb = StringBuilder()
        param.forEach { t, u ->
            sb.append(PREFIX)
                    .append(BOUNDARY)
                    .append(LINEND)
                    .append("Content-Disposition: form-data; name=\"$t\"$LINEND")
                    .append("Content-Type: text/plain; charset=$CHARSET$LINEND")
                    .append("Content-Transfer-Encoding: 8bit$LINEND")
                    .append(LINEND)
                    .append(u)
                    .append(LINEND)
        }
        val outStream = DataOutputStream(conn.outputStream)
        outStream.write(sb.toString().toByteArray())

        val end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).toByteArray()
        outStream.write(end_data)
        outStream.flush()

        val sb2 = StringBuilder()
        val input:InputStream
        val res = conn.responseCode
        if(res == 200){
            input = conn.inputStream
            val br = BufferedReader(InputStreamReader(input,"UTF-8"))
            br.forEachLine {
                sb2.append(it)
            }
        }

        outStream.close()
        conn.disconnect()
        return sb2.toString()

    }
}