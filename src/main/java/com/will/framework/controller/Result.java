package com.will.framework.controller;

import com.will.constant.ControllerCode;

/**
 * Created by Will on 2016/8/17 16:14.
 */
public class Result {

    private ControllerCode resultCode;
    private Object data;

    public Result() {
    }

    public Result(ControllerCode resultCode){
        this.resultCode = resultCode;
    }

    public Result(ControllerCode resultCode, Object data) {
        this.resultCode = resultCode;
        this.data = data;
    }

    public ControllerCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ControllerCode resultCode) {
        this.resultCode = resultCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
