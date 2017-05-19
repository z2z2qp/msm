package com.will.utils

import javax.validation.constraints.NotNull

/**
 * Created by zoumy on 2017/5/11 15:05.
 */
object StringUtils {

    /**
     * Is blank boolean.

     * @param str the str
     * *
     * @return the boolean
     * *
     * @Auth Will
     * *
     * @Date 2017 -04-10 14:41:06
     */
    fun isBlank(str: String?): Boolean {
        return str == null || str.length == 0
    }

    fun isNotBlank(str:String?):Boolean{
        return isBlank(str)
    }

    /**
     * 把字符串s2 从s1 的index位置插入

     * @param s1    the s 1
     * *
     * @param s2    the s 2
     * *
     * @param index the index
     * *
     * @return the string
     * *
     * @Auth Will
     * *
     * @Date 2017 -04-10 14:40:50
     */
    fun insert(
            @NotNull s1: String,
            @NotNull s2: String, index: Int): String {
        val sb = StringBuffer()
        sb.append(s1).insert(index, s2)
        return sb.toString()
    }
}