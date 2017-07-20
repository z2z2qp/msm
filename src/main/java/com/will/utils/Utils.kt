package com.will.utils

import java.util.*

/**
 * Created by EastLanCore on 2017/5/29.
 */

/**
 * 插入字符串
 */
fun String.insert(str:CharSequence,index:Int):String{
    val sb = StringBuilder(this)
    sb.insert(index,str)
    return sb.toString()
}

/**
 * 是否奇数
 */
fun Number.isOdd() = this.toLong() % 2 != 0L

/**
 * 集合为空或者size = 0
 */
fun Collection<*>?.isNullOrEmpty() = this == null || this.isEmpty()

/**
 * 获得随机字符串
 */
fun Random.nextString(length:Int = 16):String{
    val str = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    var code = ""
    val random = Random(Date().time)
    for (i in 0..length - 1) {
        code += str[random.nextInt(str.length)].toString()
    }
    return code
}