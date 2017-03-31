package com.example.action;

import com.example.entity.Student;
import com.example.service.UserService;
import com.will.constant.ControllerCode;
import com.will.framework.controller.Result;
import com.will.framework.controller.VerifyCodeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Will on 2016/8/15 14:30.
 */
@RequestMapping("/api/user")
@Controller
public class UserAction extends VerifyCodeController {

    private static final Logger log = LoggerFactory.getLogger(UserAction.class);
    @Resource(name="userService")
    private UserService service;

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public Result login(String loginName, String password,String code){
        log.debug("loginName:{}",loginName);
        log.debug("password:{}",password);
        Result result = checkVerifyCode(code);
        if(result.getResultCode() != ControllerCode.INVALID_VERIFY_CODE){
            Student user = service.checkUser(loginName,password);
            if(user == null){
                result = resultError(ControllerCode.INVALID_ACCOUNT);
            }else{
                result = resultOK(user);
            }
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/check",method = RequestMethod.GET)
    public Result check(String token){
        service.checkToken(token);
        return resultError(ControllerCode.INVALID_TOKEN);
    }


    @ResponseBody
    @RequestMapping(value = "/queryAll",method = RequestMethod.GET)
    public Result queryAll(int page, int rows){
        return resultOK(service.queryAllUser(page,rows));
    }

    @ResponseBody
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public Result test(String[] token){
        log.info(String.valueOf(token));
        return resultOK(token);
    }
}
