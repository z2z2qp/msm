package com.example.dao

import com.example.entity.Student
import org.apache.ibatis.annotations.Param

/**
 * Created by zoumy on 2017/5/12 9:34.
 */
interface UserDao {

    fun queryUserByLogingName(@Param(value = "loginName") loginName: String): Student

    fun queryAllUser(): List<Student>
}