package com.example.service.Impl

import com.example.dao.UserDao
import com.example.entity.Student
import com.example.service.UserService
import com.will.framework.dao.PageResult
import com.will.utils.pageinterceptor.PageHelper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * Created by zoumy on 2017/5/11 14:50.
 */
@Service("userService")
open class UserServiceImpl :UserService {

    private val log = LoggerFactory.getLogger(UserServiceImpl::class.java)

    @Resource(name = "userDao")
    private val dao: UserDao = null!!

    override fun checkUser(loginName: String, password: String): Student {
        val u = dao.queryUserByLogingName(loginName)
        return u
    }

    override fun queryAllUser(page: Int, rows: Int): PageResult<Student> {
        PageHelper.startPage(page, rows)
        dao.queryAllUser()
        return PageHelper.endPage() as PageResult<Student>
    }

    override fun checkToken(token: String): Boolean {
        return false
    }
}