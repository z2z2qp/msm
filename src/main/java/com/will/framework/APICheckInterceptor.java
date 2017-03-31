package com.will.framework;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.will.constant.ControllerCode;
import com.will.framework.controller.Result;
import com.will.utils.Value;
import com.will.utils.scure.Md5Secure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Will on 2016/12/20 10:12.
 */
public class APICheckInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(APICheckInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String,String[]> param = request.getParameterMap();
        String md5 = Value.ofEmpty(param.get("sign"));
        String sign = getSign(param);
        log.debug("md5:" + md5);
        log.debug("sign:" + sign);
        boolean result =  sign.equalsIgnoreCase(md5);
        if(!result){
            String str = JSON.toJSONString(new Result(ControllerCode.INVALID_SIGN), SerializerFeature.WriteEnumUsingToString);
            response.getWriter().print(str);
        }
        return result;
    }


    private static String getSign(Map<String,String[]> map) {
        ArrayList<String> list = new ArrayList<>();
        for(Map.Entry<String,String[]> entry:map.entrySet()){
            if("sign".equalsIgnoreCase(entry.getKey())){
                continue;
            }
            list.add(entry.getKey() + "=" + Value.ofEmpty(entry.getValue()) + "&");
            log.debug("{}:{}",entry.getKey(),Value.ofEmpty(entry.getValue()));
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result = Md5Secure.encode(result).toUpperCase();
        return result;
    }

}
