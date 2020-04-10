package com.ljryh.client.utils.excel;

import java.util.List;

/**
 * @description: list对象转二维数组 工具类
 * @author: ljryh
 **/
public class EntityToArrayUtil {

    /**
     * list对象转二维数组
     *
     * @param data
     * @param variable
     * @return
     */
    public static <T> Object[][] toArray(List<T> data, String[] variable) {
        GenericsUtils genericsUtils = new GenericsUtils();
        //动态分配一个二维数组的
        Object[][] obj = new Object[data.size()][variable.length];
        System.out.println(data.size()+"-----------------------------------");
        for (int i = 0; i < data.size(); i++) {
            final T t = data.get(i);
            for (int j = 0; j < variable.length; j++) {
                obj[i][j] = genericsUtils.getVariable(t, variable[j]);
            }
        }
        return obj;
    }

}
