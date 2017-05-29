package com.will.utils

import com.will.constant.ExceptionCode
import com.will.exception.VisionagentException
import java.util.*


/**
 * 身份证第18位计算法
 * 身份证第18位（校验码）的计算方法
 * 　　1、将前面的身份证号码17位数分别乘以不同的系数。
 *        从第一位到第十七位的系数分别为：
 *        7－9－10－5－8－4－2－1－6－3－7－9－10－5－8－4－2。
 * 　　2、将这17位数字和系数相乘的结果相加。
 * 　　3、用加出来和除以11，看余数是多少？
 * 　　4、余数只可能有
 *        0－1－2－3－4－5－6－7－8－9－10这11个数字。
 *        其分别对应的最后一位身份证的号码为
 *        1－0－X－9－8－7－6－5－4－3－2。
 * 　　5、通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
 * 　　例如：某男性的身份证号码是34052419800101001X。我们要看看这个身份证是不是合法的身份证。
 * 　　首先我们得出前17位的乘积和是189，然后用189除以11得出的结果是17+2/11，也就是说其余数是2。最后通过对应规则就可以知道余数2对应的数字是x。所以，可以判定这是一个合格的身份证号码。
 *
 * @author teacher
 */
object IDCardUtils{

    /**
     * 系数
     */
    private val w = intArrayOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)
    /**
     * 校验码
     */
    private val code = "10X98765432".toCharArray()

    /**
     * The entry point of application.

     * @param args the input arguments
     * *
     * @Auth Will
     * *
     * @Date 2016 -08-22 15:58:34
     */
    @JvmStatic fun main(args: Array<String>) {
        val id = "33041119910127523X"
        val pass = verify(id)
        println(pass)
        println(fifteen2Eighteen("330411910127523"))
        println(isMan(id))
        println(getBirthday("330411910127523"))
    }


    /**
     * Verify 校验省份证号码

     * @param id the id
     * *
     * @return the boolean
     * *
     * @Auth Will
     * *
     * @Date 2016 -08-22 15:58:34
     */
    fun verify(id: String): Boolean {
        if (id.length != 18) {
            throw VisionagentException(ExceptionCode.ERROR_ID_CARD_LENGTH)
        }
        val ch = id.toCharArray()
        val c = getChecksum(ch)
        return ch[17] == c
    }

    /**
     * 15位身份证号升级成18位

     * @param id the id
     * *
     * @return the string
     * *
     * @Auth Will
     * *
     * @Date 2016 -08-22 15:58:34
     */
    fun fifteen2Eighteen(id: String): String {
        var newId = id
        var strYear = newId.substring(6, 8)
        if (strYear.startsWith("0|1")) {
            strYear = "20"
        } else {
            strYear = "19"
        }
        newId = newId.insert(strYear, 6)
        return newId + getChecksum(newId.toCharArray())
    }

    /**
     * 计算校验码
     * @param ch
     * *
     * @return
     */
    private fun getChecksum(ch: CharArray): Char {
        //第一步：前17位加权求和
        var sum = 0
        for (i in 0..16) {
            sum += (ch[i] - '0') * w[i]
        }
        //第二步：获取校验码值
        return code[sum % 11]
    }


    /**
     * 判断是否为男性

     * @param id the id
     * *
     * @return the boolean
     * *
     * @Auth Will
     * *
     * @Date 2016 -08-22 16:07:38
     */
    fun isMan(id: String): Boolean {
        val c:Char
        if (id.length == 15) {
            c = id[14]
        } else if (id.length == 18) {
            c = id[16]
        } else {
            throw VisionagentException(ExceptionCode.ERROR_ID_CARD_LENGTH)
        }
        return (c - '0').isOdd()
    }

    /**
     * 得到生日

     * @param id the id
     * *
     * @return the date
     * *
     * @Auth Will
     * *
     * @Date 2016 -08-22 16:46:01
     */
    fun getBirthday(id: String): Date {
        var date: Date?
        if (id.length == 18) {
            val birthDay = id.substring(6, 14)
            date = DateTimeUtils.getDateByStringFmt(birthDay, "yyyyMMdd")
        } else if (id.length == 15) {
            val birthDay = id.substring(6, 12)
            date = DateTimeUtils.getDateByStringFmt(birthDay, "yyMMdd")
        } else {
            throw VisionagentException(ExceptionCode.ERROR_ID_CARD_LENGTH)
        }
        return date
    }

}