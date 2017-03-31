package com.will.utils;

import java.util.Map;

/**
 * Created by Will on 2016/8/24 15:13.
 */
public class MapUtils {
    public static boolean isNotEmpty(Map<?,?> param) {
        return param != null && param.size() > 0;
    }
}
