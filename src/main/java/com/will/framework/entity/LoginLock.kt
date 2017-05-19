package com.will.framework.entity

/*
 * Created by zoumy on 2017/5/11 11:10.
 */
class LoginLock() : BaseEntity() {


    var loginName: String? = null
    var loginCount: Int? = null
    var lockTime: Long? = null

    constructor(loginName:String,loginCount:Int,lockTime:Long):this(){
        this.lockTime = lockTime
        this.loginCount = loginCount
        this.loginName = loginName
    }
}