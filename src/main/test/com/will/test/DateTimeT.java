package com.will.test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Created by Will on 2017/4/1 10:18.
 */
public class DateTimeT {
    public static void main(String[] args) {
        Number l1 = LocalDateTime.now().plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime.plusMinutes(10);
        System.out.println(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Long l2 = System.currentTimeMillis();
        System.out.println(l1);
        System.out.println(l2);
        String str = String.valueOf(System.currentTimeMillis());
        System.out.println(str.length());
        String ldt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddmm"));
        System.out.println(ldt);
    }
}
