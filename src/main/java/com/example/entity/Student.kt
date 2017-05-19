package com.example.entity

import com.will.framework.entity.BaseEntity
import java.util.*

/**
 * Created by zoumy on 2017/5/12 9:30.
 */
class Student() : BaseEntity() {

    var stuid: Int? = null
    var romid: Int? = null
    var name: String? = null
    var age: Int? = null
    var born: Date? = null

    constructor(stuid:Int, name:String,age:Int):this(){
        this.stuid = stuid
        this.name = name
        this.age = age
    }

    constructor(stuid:Int, name:String,age:Int, romid: Int, born:Date):this(stuid, name,age){
        this.romid = romid
        this.born = born
    }
}