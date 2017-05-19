package com.will.utils.scure

import sun.misc.BASE64Decoder
import sun.misc.BASE64Encoder
import java.io.UnsupportedEncodingException

/**
 * Created by zoumy on 2017/5/12 12:59.
 */
object Base64Secure {
    // 加密
    fun encode(str: String): String? {
        var b: ByteArray? = null
        var s: String? = null
        try {
            b = str.toByteArray()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        if (b != null) {
            s = BASE64Encoder().encode(b)
        }
        return s
    }

    // 解密
    fun decode(s: String?): String? {
        var b: ByteArray?
        var result: String? = null
        if (s != null) {
            val decoder = BASE64Decoder()
            try {
                b = decoder.decodeBuffer(s)
                result = String(b)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return result
    }
}