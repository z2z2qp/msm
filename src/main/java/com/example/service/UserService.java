package com.example.service;

import com.example.entity.Student;
import com.will.framework.dao.PageResult;
import com.will.framework.service.BaseService;

/**
 * Created by Will on 2016/8/15 14:32.
 */
public interface UserService extends BaseService {
    Student checkUser(String loginName, String password);

    PageResult<Student> queryAllUser(int page, int rows);
}
