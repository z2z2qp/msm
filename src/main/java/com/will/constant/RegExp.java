package com.will.constant;

/**
 * Created by Will on 2016/8/19 14:35.
 */
public class RegExp {

    /**
     * 手机号
     */
    public static final String PHONE = "^1[3|4|5|7|8]\\d{9}$";
    /**
     * 中文字符
     */
    public static final String CHINESE_CHARACTER  = "[u4e00-u9fa5]";
    /**
     * 双字节字符
     */
    public static final String DOUBLE_CHARACTER = "[^x00-xff]";
    /**
     * html标签
     */
    public static final String HTML = "<(S*?)[^>]*>.*?|<.*? /> ";
    /**
     * 电子邮件
     */
    public static final String EMAIL = "w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*";
    /**
     * url
     */
    public static final String URL = "[a-zA-z]+://[^s]*";
    /**
     * qq号
     */
    public static final String QQ = "[1-9][0-9]{4,}";
    /**
     * 邮编
     */
    public static final String ZIP = "[1-9]d{5}(?!d)";
    /**
     * 域名
     */
    public static final String DOMAIN = "[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(/.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+/.?";
    /**
     * 账户允许大小写字母数字和下划线，4到15个
     */
    public static final String ACCOUNT = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";
    /**
     * ipv4
     */
    public static final String IPv4 = "\\d+\\.\\d+\\.\\d+\\.\\d+";
    /**
     * ipv6
     */
    public static final String IPv6 = "((?:(?:25[0-5]|2[0-4]\\\\d|[01]?\\\\d?\\\\d)\\\\.){3}(?:25[0-5]|2[0-4]\\\\d|[01]?\\\\d?\\\\d))";
    /**
     * 国内电话号码
     */
    public static final String TEL = "\\d{3}-\\d{8}|\\d{4}-\\d{7}";
}
