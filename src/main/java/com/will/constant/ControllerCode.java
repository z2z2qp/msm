package com.will.constant;

/**
 * Created by Will on 2016/8/18 11:24.
 * Controller层返回码枚举类
 */
public enum ControllerCode {

    OK(1,""),
    INVALID_ACCOUNT(101,"用户名或密码错误"),
    INVALID_TOKEN(103,"无效的token"),
    INVALID_SIGN(104,"签名错误"),
    INVALID_VERIFY_CODE(105,"验证码错误");

    private int code;
    private String message;
    ControllerCode(int code,String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "{\"code\":\""+this.code+"\",\"message\":\""+this.message+"\"}";
    }
}
