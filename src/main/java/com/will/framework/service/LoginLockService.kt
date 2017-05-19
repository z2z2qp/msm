package com.will.framework.service

import com.will.framework.entity.LoginLock

/**
 * Created by zoumy on 2017/5/11 14:55.
 */
interface LoginLockService :BaseService {

    abstract fun getLoginLockByLoginName(loginName: String): LoginLock

    abstract fun saveOrUpdate(loginLock: LoginLock)


    override fun checkToken(token: String): Boolean {
        return true
    }
}