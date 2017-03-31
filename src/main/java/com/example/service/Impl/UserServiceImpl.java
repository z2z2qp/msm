package com.example.service.Impl;

import com.example.dao.UserDao;
import com.example.entity.Student;
import com.example.service.UserService;
import com.will.utils.pageinterceptor.Page;
import com.will.utils.pageinterceptor.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Will on 2016/8/15 14:33.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource(name="userDao")
    private UserDao dao;
    @Override
    public Student checkUser(String loginName, String password) {
        Student u = dao.queryUserByLogingName(loginName);
        log.debug("user:{}",u);
        return u;
    }

    @Override
    public Page<Student> queryAllUser(int page, int rows) {
        PageHelper.startPage(page,rows);
        dao.queryAllUser();
        return PageHelper.endPage();
    }

    @Override
    public boolean checkToken(String token) {
        return false;
    }
}
