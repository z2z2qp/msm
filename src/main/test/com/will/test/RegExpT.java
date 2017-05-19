package com.will.test;

import com.will.constant.RegExp;
import com.will.constant.Version;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zoumy on 2017/4/19 14:12.
 */
public class RegExpT {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile(RegExp.INSTANCE.getIPv4());
        Matcher matcher = pattern.matcher("0.0.0.254");
        System.out.println(matcher.find());
        System.out.println(Version.INSTANCE.getVERSION_CODE());
    }
}
