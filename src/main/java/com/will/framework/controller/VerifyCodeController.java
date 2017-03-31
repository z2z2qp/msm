package com.will.framework.controller;

import com.will.constant.Constant;
import com.will.constant.ControllerCode;
import com.will.utils.Value;
import com.will.utils.VerifyCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Will on 2017/3/30 16:10.
 */
@RequestMapping("/verifyCode")
@Controller
public class VerifyCodeController extends BaseController {


    /**
     * 验证码
     *
     * @param width      the width
     * @param height     the height
     * @param verifySize the verify size
     * @Auth Will
     * @Date 2017 -03-30 16:31:46
     */
    @ResponseBody
    @RequestMapping(value = "/verifyCode",method = RequestMethod.GET)
    public void verifyCode(Integer width,Integer height,Integer verifySize){
        try {
            width = Value.of(width,100);
            height = Value.of(height,40);
            verifySize = Value.of(verifySize,4);
            OutputStream os = response.getOutputStream();
            String code = VerifyCodeUtils.outputVerifyImage(width,height,os,verifySize);
            session.setAttribute(Constant.VERIFY_CODE,code);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验验证码
     *
     * @param code the code
     * @return the boolean
     * @Auth Will
     * @Date 2017 -03-30 16:32:06
     */
    @ResponseBody
    @RequestMapping(value = "/checkVerifyCode",method = RequestMethod.GET)
    protected Result checkVerifyCode(String code){
        String verifyCode = (String) session.getAttribute(Constant.VERIFY_CODE);
        if(verifyCode != null && verifyCode.equalsIgnoreCase(code))
            return new Result(ControllerCode.OK);
        else{
            return resultError(ControllerCode.INVALID_VERIFY_CODE);
        }
    }
}
