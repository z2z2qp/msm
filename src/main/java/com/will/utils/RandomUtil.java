package com.will.utils;

import java.util.Date;
import java.util.Random;

/**
 * The type RandomUtil.
 *
 * @Auth Will
 * @Date 2016 -08-18 10:47:18
 */
public class RandomUtil {

    private static String randomString = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";


    /**
     * Gen random code string.
     *
     * @param length the length
     * @return the string
     * @Auth Will
     * @Date 2016 -08-18 10:47:18
     */
    public static String getRandomCode(int length) {
        return getRandomCode(randomString, length);
    }

    /**
     * Gen random code string.
     *
     * @param seedstring the seedstring
     * @param length     the length
     * @return the string
     * @Auth Will
     * @Date 2016 -08-18 10:47:18
     */
    public static String getRandomCode(String seedstring, int length) {
        String code = "";
        Random random = new Random(new Date().getTime());
        for (int i = 0; i < length; i++) {
            code += String.valueOf(seedstring.charAt(random.nextInt(seedstring.length())));
        }
        return code;
    }
}
