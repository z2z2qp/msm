package com.will.framework;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Will on 2016/8/15 16:56.
 */
public class FastJsonWithFilterHttpMessageConverter extends
        FastJsonHttpMessageConverter {
    private SerializeFilter filter = null;

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        OutputStream out = outputMessage.getBody();
        String text = null;
        if (getFilter() != null) //使用自定义的拦截器进行json转换时的拦截
            text = JSON.toJSONString(obj, getFilter(), getFeatures());
        else
            text = JSON.toJSONString(obj, getFeatures());

        int i = text.indexOf("\"{\"");
        int i2 = text.indexOf("\"}\"");
        String str = text.substring(0,i)+text.substring(i+1,i2+2)+text.substring(i2+3);
        byte[] bytes = str.getBytes(getCharset());
        out.write(bytes);
    }

    public SerializeFilter getFilter() {
        return filter;
    }

    public void setFilter(SerializeFilter filter) {
        this.filter = filter;
    }
}
