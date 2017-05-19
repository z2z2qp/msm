package com.will.utils.Tree

import java.util.*

/**
 * Created by zoumy on 2017/5/11 8:35.
 */
class Tree() {
    var id:String? = null
    var text:String? = null
    var state:String = ""
    var pid:String? = null
    var children:ArrayList<Tree> = ArrayList()

    constructor(id:String,text:String,pid:String?) : this(){
        this.id = id
        this.text = text
        this.pid = pid
    }

    override fun toString(): String {
        return "{id:${id},text:${text},state:${state},pid:${pid},children:${children.toString()}}"
    }
}