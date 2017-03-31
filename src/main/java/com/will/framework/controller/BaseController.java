package com.will.framework.controller;

import com.will.constant.ControllerCode;
import com.will.constant.ExceptionCode;
import com.will.exception.VisionagentException;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Will on 2016/8/17 16:12.
 */
public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    @ModelAttribute
    private void setRRS(HttpServletRequest request,HttpServletResponse response){
        this.request = request;
        this.response = response;
        setSession(request.getSession());
    }

    protected void setSession(HttpSession session){
        this.session = session;
    }

    /**
     * 构造正确返回
     * @param data
     * @return
     */
    protected Result resultOK(Object data){
        return new Result(ControllerCode.OK,data);
    }

    /**
     * 构造错误返回
     * @param code
     * @return
     */
    protected Result resultError(ControllerCode code){
        if(ControllerCode.OK.equals(code)){
            throw new VisionagentException(ExceptionCode.CONTROLLER_RETURN_ERROR);
        }
        return new Result(code);
    }

}
