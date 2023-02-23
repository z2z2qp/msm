package com.will.utils.scure

import java.io.UnsupportedEncodingException
import java.util.Base64

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
            s = String(Base64.getEncoder().encode(b))
        }
        return s
    }

    // 解密
    fun decode(s: String?): String? {
        val b: ByteArray?
        var result: String? = null
        if (s != null) {
            try {
                b = Base64.getDecoder().decode(s)
                result = String(b)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return result
    }
}