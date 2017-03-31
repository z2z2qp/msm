package com.example.service;

import com.example.entity.Student;
import com.will.framework.service.BaseService;
import com.will.utils.pageinterceptor.Page;

/**
 * Created by Will on 2016/8/15 14:32.
 */
public interface UserService extends BaseService {
    Student checkUser(String loginName, String password);

    Page<Student> queryAllUser(int page, int rows);
}
