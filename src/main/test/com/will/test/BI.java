package com.will.test;

import java.math.BigInteger;
import java.text.NumberFormat;

/**
 * Created by Will on 2017/4/10 16:43.
 */
public class BI {

    public static void main(String[] args) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        BigInteger bigInteger = new BigInteger("2");
        for(int i = 0;i<7;i++){
            bigInteger =  bigInteger.multiply(new BigInteger("90"));
            System.out.printf("第 %d 天 收入 %s\n",i+1, nf.format(bigInteger.longValue()));
        }
    }
}
