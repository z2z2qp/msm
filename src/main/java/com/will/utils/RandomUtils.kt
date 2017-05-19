package com.will.utils

import java.util.*

/**
 * Created by zoumy on 2017/5/12 12:40.
 */
object RandomUtils {

    private val randomString = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"


    /**
     * Gen random code string.

     * @param length the length
     * *
     * @return the string
     * *
     * @Auth Will
     * *
     * @Date 2016 -08-18 10:47:18
     */
    fun getRandomCode(length: Int): String {
        return getRandomCode(randomString, length)
    }

    /**
     * Gen random code string.

     * @param seedstring the seedstring
     * *
     * @param length     the length
     * *
     * @return the string
     * *
     * @Auth Will
     * *
     * @Date 2016 -08-18 10:47:18
     */
    fun getRandomCode(seedstring: String, length: Int): String {
        var code = ""
        val random = Random(Date().time)
        for (i in 0..length - 1) {
            code += seedstring[random.nextInt(seedstring.length)].toString()
        }
        return code
    }
}