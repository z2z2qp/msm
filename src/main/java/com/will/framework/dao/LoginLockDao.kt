package com.will.framework.dao

import com.will.framework.entity.LoginLock
import org.apache.ibatis.annotations.Param

/**
 * Created by zoumy on 2017/5/11 12:35.
 */
interface LoginLockDao {

    fun getLoginLockByLoginName(@Param(value = "loginName") loginName: String): LoginLock?

    fun saveOrUpdate(loginLock: LoginLock)
}