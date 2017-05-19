package com.will.utils

/**
 * Created by zoumy on 2017/5/12 12:38.
 */
object MapUtils {
    fun isNotEmpty(param: Map<*, *>?): Boolean {
        return param != null && param.isNotEmpty()
    }

}