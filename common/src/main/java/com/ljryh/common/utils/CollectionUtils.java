package com.ljryh.common.utils;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionUtils {
    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    public static void main(String[] args) {
        System.out.println(CollectionUtils.isEmpty(new ArrayList()));
    }
}
