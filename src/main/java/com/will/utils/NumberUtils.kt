package com.will.utils

/**
 * Created by zoumy on 2017/5/12 12:37.
 */
object NumberUtils {

    /**
     * 是否为偶数

     * @param number the number
     * *
     * @return the boolean
     * *
     * @Auth Will
     * *
     * @Date 2016 -12-13 16:12:16
     */
    fun isOdd(number: Number): Boolean {
        return number.toLong() % 2 != 0L
    }
}