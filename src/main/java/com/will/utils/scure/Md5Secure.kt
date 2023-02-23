package com.will.utils.scure

import com.will.utils.HexByteUtils
import java.security.MessageDigest

/**
 * Created by zoumy on 2017/5/12 12:26.
 */
object Md5Secure {
    /**
     * 将plaintext转化为md5值
     * @param plaintext
     * *
     * @return
     */
    fun encode(plaintext: String?): String? {
        if (plaintext != null) {
            try {
                val md = MessageDigest.getInstance("MD5")
                md.reset()
                md.update(plaintext.toByteArray())
                val results = md.digest()
                return HexByteUtils.byteArrayToHexString(results)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }
        return null
    }
}