package com.will.utils;

import java.util.Collection;

/**
 * Created by Will on 2016/9/5 13:54.
 */
public class CollectionUtils {
    public static boolean isEmpty(Collection<?> distances) {
        return distances == null || distances.size() == 0;
    }
}
