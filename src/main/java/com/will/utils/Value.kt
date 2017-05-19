package com.will.utils

import java.util.Arrays.toString

/**
 * Created by zoumy on 2017/5/11 15:16.
 */
object Value {

    val INTEGER_NULL: Int? = null
    val DOUBLE_NULL: Double? = null
    val STRING_NULL: String? = null
    val LONG_NULL: Long? = null
    val BOOLEAN_NULL: Boolean? = null
    val INTEGER_ZERO = Integer.valueOf(0)
    val DOUBLE_ZERO: Double? = java.lang.Double.valueOf(0.0)
    val LONG_ZERO = java.lang.Long.valueOf(0L)

    /**
     * 获取对象的String值

     * @param obj the obj
     * *
     * @param def the def 如果对象不能被转换为String是返回的默认值
     * *
     * @return the string
     * *
     * @Auth Will
     * *
     * @Date 2016 -10-13 09:50:06
     */
    fun of(obj: Any?, def: String): String {
        var result = def
        if (obj != null) {
            if (obj.javaClass.isArray) {
                val className = obj.javaClass.simpleName.toLowerCase()
                when (className) {
                    "int[]" -> result = toString(obj as IntArray?)
                    "short[]" -> result = toString(obj as ShortArray?)
                    "byte[]" -> result = toString(obj as ByteArray?)
                    "long[]" -> result = toString(obj as LongArray?)
                    "double[]" -> result = toString(obj as DoubleArray?)
                    "float[]" -> result = toString(obj as FloatArray?)
                    "char[]" -> result = toString(obj as CharArray?)
                    else -> result = toString(obj as Array<*>?)
                }

                result = result.substring(1, result.length - 1)
            } else {
                result = obj.toString()
            }
        }
        return result
    }

    /**
     * 对象转换为 integer.

     * @param obj the obj
     * *
     * @param def the def 如果对象不能被转换为integer是返回的默认值
     * *
     * @return the integer
     * *
     * @Auth Will
     * *
     * @Date 2016 -10-13 09:50:06
     */
    fun of(obj: Any?, def: Int?): Int? {
        var result = def
        try {
            if (obj != null) {
                result = Integer.valueOf(obj.toString())
            }
        } catch (e: Exception) {
        }

        return result
    }

    /**
     * 对象转换为 double.

     * @param obj the obj
     * *
     * @param def the def 如果对象不能被转换为double是返回的默认值
     * *
     * @return the double
     * *
     * @Auth Will
     * *
     * @Date 2016 -10-13 09:50:06
     */
    fun of(obj: Any?, def: Double?): Double? {
        var result = def
        try {
            if (obj != null) {
                result = java.lang.Double.valueOf(obj.toString())
            }
        } catch (e: Exception) {
        }

        return result
    }

    /**
     * 对象转换为 long.

     * @param obj the obj
     * *
     * @param def the def 如果对象不能被转换为long是返回的默认值
     * *
     * @return the long
     * *
     * @Auth Will
     * *
     * @Date 2016 -10-13 09:50:06
     */
    fun of(obj: Any?, def: Long?): Long? {
        var result = def
        try {
            if (obj != null) {
                result = java.lang.Long.valueOf(obj.toString())
            }
        } catch (e: Exception) {
        }

        return result
    }

    /**
     * 对象转换为 boolean.

     * @param obj the obj
     * *
     * @param def the def 如果对象不能被转换为boolean是返回的默认值
     * *
     * @return the boolean
     * *
     * @Auth Will
     * *
     * @Date 2016 -10-13 09:50:06
     */
    fun of(obj: Any?, def: Boolean?): Boolean? {
        var result = def
        try {
            if (obj != null) {
                result = java.lang.Boolean.valueOf(obj.toString())
            }
        } catch (e: Exception) {
        }

        return result
    }

    fun ofEmpty(key: Any?): String {
        return of(key, "")
    }
}