package com.will.utils

/**
 * Created by zoumy on 2017/5/12 12:39.
 */
object CollectionUtils {
    fun isEmpty(distances: Collection<*>?): Boolean {
        return distances == null || distances.isEmpty()
    }
    fun  isNoteEmpty(distances: Collection<*>?):Boolean{
        return !isEmpty(distances)
    }
}