package com.example.dao;

import com.example.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Will on 2016/8/15 13:16.
 */
public interface UserDao {

    Student queryUserByLogingName(@Param(value = "loginName") String loginName);

    List<Student> queryAllUser();
}
