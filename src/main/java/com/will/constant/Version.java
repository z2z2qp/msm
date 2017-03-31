package com.will.constant;

/**
 * Created by zoumy on 2017/1/9 16:06.
 */
public final class Version {
    private static final String VERSION_CODE = "1.0.0";

    public static String code(){
        return VERSION_CODE;
    }

    public static void main(String[] args) {
        System.out.println(code());
    }
}
