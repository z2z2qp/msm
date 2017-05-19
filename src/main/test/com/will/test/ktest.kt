package com.will.test

import com.will.utils.DateTimeUtils
import com.will.utils.scure.Md5Secure
import java.util.*

/**
 * Created by zoumy on 2017/5/10 17:11.
 */

fun main(args:Array<String>){
    val a = DateTimeUtils.getQuarterDate()
    val b = DateTimeUtils.getDuration(DateTimeUtils.getDateByStringFmt("2017-05-12"), Date())
    println(a)
    println(b.toHours())
    println(b.toDays())
    val s = Md5Secure.encode("111111")
    println(s)
}
