package com.will.utils.scure

import com.will.utils.HexByteUtils
import com.will.utils.StringUtils
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec


/**
 * Created by zoumy on 2017/5/12 12:44.
 */
object AesSecure{

    /**
     * AES 加密
     */
    fun encode(plaintText:String,secKey:String):String?{
        if (StringUtils.isNotBlank(plaintText) && StringUtils.isNotBlank(secKey)){
            try {
                val  kgen = KeyGenerator.getInstance("AES")
                kgen.init(128, SecureRandom(secKey.toByteArray()))
                val secretKey = kgen.generateKey()
                val enCodeFormat = secretKey.encoded
                val key = SecretKeySpec(enCodeFormat,"AES")

                val cipher = Cipher.getInstance("AES")
                val byteContent = plaintText.toByteArray()
                cipher.init(Cipher.ENCRYPT_MODE,key)

                val result = cipher.doFinal(byteContent)
                return HexByteUtils.byteArrayToHexString(result)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        return null
    }

    /**
     * AES 解密
     */
    fun decode(cipherText:String,secKey:String):String?{
        if (StringUtils.isNotBlank(cipherText) && StringUtils.isNotBlank(secKey)){
            try {
                val kgen = KeyGenerator.getInstance("AES")
                kgen.init(128, SecureRandom(secKey.toByteArray()))
                val secretKey = kgen.generateKey()
                val enCodeFormat = secretKey.encoded
                val key = SecretKeySpec(enCodeFormat, "AES")
                val cipher = Cipher.getInstance("AES")
                cipher.init(Cipher.DECRYPT_MODE, key)
                val result = cipher.doFinal(HexByteUtils.hexStringToByteArray(cipherText))
                return String(result)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        return null
    }
}