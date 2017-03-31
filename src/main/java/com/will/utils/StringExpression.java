package com.will.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HQL语句适配器
 *
 * @author DongJun
 */
public class StringExpression {
    private static final Logger log = LoggerFactory.getLogger(StringExpression.class);

    private static Properties properties;

    private String dft_token = "?";
    protected String str = null;

    static{
        init();
    }
    /**
     * 默认构造
     *
     * @param sqlId
     */
    //change by Will at 2016年8月24日14:58:47
    public StringExpression(String sqlId) {
        this.str = getSql(sqlId);
    }

    /**
     * 替换StringExpression表达式中的第一个默认TOKEN为params对应的值以逗号分隔
     * 默认TOKEN为问号？
     *
     * @param params
     * @return 自身
     */
    public StringExpression r(List<String> params) {
        return this.r(dft_token, params);
    }

    /**
     * 替换StringExpression表达式中每一个token为默认的params中的值
     *
     * @param token
     * @param params
     * @return 自身
     */
    public StringExpression r(String token, List<String> params) {
        if (str.contains(token)) {
            String strjoin = "";
            for (int i = 0; i < params.size(); i++) {
                strjoin += params.get(i);
                if (i != params.size() - 1)
                    strjoin += ",";
            }
            replaceFirst(token, strjoin);
        }
        return this;
    }

    /**
     * 替换StringExpression表达式中的第一一个TOKEN为params中的key的为params中对应的值
     * 默认TOKEN为问号？
     *
     * @param value
     * @return
     */
    public StringExpression r(String value) {
        return this.r(dft_token, value);
    }

    /**
     * 替换StringExpression表达式中的第一一个TOKEN为params中的key的为params中对应的值
     *
     * @param token
     * @param value
     * @return
     */
    public StringExpression r(String token, String value) {
        if (str.contains(token)) {
            replaceFirst(token, value);
        }
        return this;
    }

    /**
     * 替换StringExpression表达式中的每一个TOKEN为params中的key的为params中对应的值
     *
     * @param params
     * @return 自身
     */
    public StringExpression r(Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            if (str.contains(key)) {
                replaceFirst(key, val);
            }
        }
        return this;
    }

    /**
     * 将start,end中间的字符串替换为value，start及end不变
     * <p/>
     * 如select 1,2,3,4,5, from wwww可将select及from中的字符串替换
     *
     * @param start
     * @param end
     * @param value
     * @return
     */
    public StringExpression r(String start, String end, String value) {
        int sidx = this.str.indexOf(start);
        int eidx = this.str.indexOf(end);
        if (sidx >= 0 && eidx >= 0) {
            sidx += start.length();
            if (sidx < eidx) {
                str = str.substring(0, sidx) + value + str.substring(eidx);
            }
        }
        return this;
    }

    /**
     * 转换成string对象
     */
    @Override
    public String toString() {
        log.info("sql:{}",str);
        return str;
    }

    /**
     * 替换
     *
     * @param target
     * @param replacement
     */
    private void replaceFirst(CharSequence target, CharSequence replacement) {
        this.str = Pattern.compile(target.toString(), Pattern.LITERAL).matcher(
                this.str).replaceFirst(Matcher.quoteReplacement(replacement.toString()));
    }

    public String getDftToken() {
        return this.dft_token;
    }

    public StringExpression setDftToken(String token) {
        this.dft_token = token;
        return this;
    }


    private String getSql(String sqlId){
        if(properties == null){
            init();
        }
        return (String) properties.get(sqlId);
    }

    private static void init(){

        properties = new Properties();
        try {
            InputStream in = StringExpression.class.getResourceAsStream("/sql.properties");
            properties.load(in);
        } catch (IOException e) {
            log.error("读取配置文件创建错误",e);
        } catch (Exception e) {
            log.error("other Exception",e);
        }
    }

}
