package com.will.framework.controller

import com.will.constant.ControllerCode

/**
 * Created by zoumy on 2017/5/11 13:02.
 */
class Result(resultCode:ControllerCode) {


    /**
     * 错误码
     */
    var resultCode: ControllerCode = resultCode
    /**
     * 数据
     */
    var data: Any? = null

    constructor(resultCode: ControllerCode,data:Any):this(resultCode){
        this.data = data
    }
}