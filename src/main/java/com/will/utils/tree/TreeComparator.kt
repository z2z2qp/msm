package com.will.utils.tree

/**
 * Created by zoumy on 2017/5/11 8:41.
 */
class TreeComparator:Comparator<Tree> {

    override fun compare(t1: Tree?, t2: Tree?): Int {
        return t1!!.id!!.hashCode() - t2!!.id!!.hashCode()
    }
}