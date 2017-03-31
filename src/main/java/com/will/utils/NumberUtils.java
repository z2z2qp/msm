package com.will.utils;

/**
 * Created by Will on 2016/8/22 16:04.
 */
public class NumberUtils {

    /**
     * 是否为偶数
     *
     * @param number the number
     * @return the boolean
     * @Auth Will
     * @Date 2016 -12-13 16:12:16
     */
    public static boolean isOdd(Number number){
        return number.longValue() % 2 != 0;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @Auth Will
     * @Date 2016 -08-22 16:48:13
     */
    public static void main(String[] args) {
        System.out.println(isOdd(11));
    }
}
