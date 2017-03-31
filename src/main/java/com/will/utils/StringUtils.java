package com.will.utils;


import javax.validation.constraints.NotNull;

/**
 * Created by Will on 2016/8/17 9:01.
 */
public class StringUtils {

    public static boolean isBlank(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 把字符串s2 从s1 的index位置插入
     *
     * @param s1
     * @param s2
     * @param index
     * @return
     */
    public static String insert(
            @NotNull String s1,
            @NotNull String s2, int index) {
        StringBuffer sb = new StringBuffer();
        sb.append(s1).insert(index, s2);
        return sb.toString();
    }
}
