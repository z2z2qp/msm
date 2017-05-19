package com.will.framework.service.impl

import com.will.framework.dao.LoginLockDao
import com.will.framework.entity.LoginLock
import com.will.framework.service.LoginLockService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import javax.annotation.Resource

/**
 * Created by zoumy on 2017/5/11 14:55.
 */
@Service("loginLockService")
open class LoginLockServiceImpl: LoginLockService {

    private val log = LoggerFactory.getLogger(LoginLockServiceImpl::class.java)

    @Resource(name = "loginLockDao")
    private val dao: LoginLockDao? = null

    override fun getLoginLockByLoginName(loginName: String): LoginLock {
        var ll = dao!!.getLoginLockByLoginName(loginName)
        if (ll == null) {
            ll = LoginLock(loginName, 0, LocalDateTime.now().plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
        }
        return ll
    }

    override fun saveOrUpdate(loginLock: LoginLock) {
        loginLock.lockTime = LocalDateTime.now().plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        dao!!.saveOrUpdate(loginLock)
    }
}