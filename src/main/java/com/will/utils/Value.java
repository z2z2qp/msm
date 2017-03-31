package com.will.utils;

import java.util.Arrays;

/**
 * Created by Will on 2016/10/11 17:06.
 */
public class Value {

    public static final Integer INTEGER_NULL = null;
    public static final Double DOUBLE_NULL = null;
    public static final String STRING_NULL = null;
    public static final Long LONG_NULL = null;
    public static final Boolean BOOLEAN_NULL = null;
    public static final Integer INTEGER_ZERO = Integer.valueOf(0);
    public static final Double DOUBLE_ZERO = Double.valueOf(0.0);
    public static final Long LONG_ZERO = Long.valueOf(0L);

    /**
     * 获取对象的String值
     *
     * @param obj the obj
     * @param def the def 如果对象不能被转换为String是返回的默认值
     * @return the string
     * @Auth Will
     * @Date 2016 -10-13 09:50:06
     */
    public static String of(Object obj,String def){
        String result = def;
        if (obj != null) {
            if (obj.getClass().isArray()) {
                String className = obj.getClass().getSimpleName().toLowerCase();
                switch (className) {
                    case "int[]":
                        result = Arrays.toString((int[]) obj);
                        break;
                    case "short[]":
                        result = Arrays.toString((short[]) obj);
                        break;
                    case "byte[]":
                        result = Arrays.toString((byte[]) obj);
                        break;
                    case "long[]":
                        result = Arrays.toString((long[]) obj);
                        break;
                    case "double[]":
                        result = Arrays.toString((double[]) obj);
                        break;
                    case "float[]":
                        result = Arrays.toString((float[]) obj);
                        break;
                    case "char[]":
                        result = Arrays.toString((char[]) obj);
                        break;
                    default:
                        result = Arrays.toString((Object[]) obj);
                        break;
                }

                result = result.substring(1, result.length() - 1);
            } else {
                result = obj.toString();
            }
        }
        return result;
    }

    /**
     * 对象转换为 integer.
     *
     * @param obj the obj
     * @param def the def 如果对象不能被转换为integer是返回的默认值
     * @return the integer
     * @Auth Will
     * @Date 2016 -10-13 09:50:06
     */
    public static Integer of(Object obj,Integer def){
        Integer result = def;
        try {
            if(obj != null){
                result = Integer.valueOf(obj.toString());
            }
        } catch (Exception e) {
        }
        return  result;
    }

    /**
     * 对象转换为 double.
     *
     * @param obj the obj
     * @param def the def 如果对象不能被转换为double是返回的默认值
     * @return the double
     * @Auth Will
     * @Date 2016 -10-13 09:50:06
     */
    public static Double of(Object obj,Double def){
        Double result = def;
        try {
            if(obj != null){
                result = Double.valueOf(obj.toString());
            }
        } catch (Exception e) {
        }
        return  result;
    }

    /**
     * 对象转换为 long.
     *
     * @param obj the obj
     * @param def the def 如果对象不能被转换为long是返回的默认值
     * @return the long
     * @Auth Will
     * @Date 2016 -10-13 09:50:06
     */
    public static Long of(Object obj,Long def){
        Long result = def;
        try {
            if(obj != null){
                result = Long.valueOf(obj.toString());
            }
        } catch (Exception e) {
        }
        return  result;
    }

    /**
     * 对象转换为 boolean.
     *
     * @param obj the obj
     * @param def the def 如果对象不能被转换为boolean是返回的默认值
     * @return the boolean
     * @Auth Will
     * @Date 2016 -10-13 09:50:06
     */
    public static Boolean of(Object obj,Boolean def){
        Boolean result = def;
        try {
            if(obj != null){
                result = Boolean.valueOf(obj.toString());
            }
        } catch (Exception e) {
        }
        return  result;
    }

    public static String ofEmpty(Object key) {
        return of(key,"");
    }
}
