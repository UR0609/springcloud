package com.ljryh.client.utils.excel;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class GenericsUtils<T> implements Base<T> {
    @Override
    public String getVariable(T t, String variable) {

        Class<?> beanClass = t.getClass();
        try {
            // 获取属性值(方法一)
            PropertyDescriptor pd = new PropertyDescriptor(variable, beanClass);
            Method rm = pd.getReadMethod();
            return rm.invoke(t).toString();
//            // 获取属性值(方法二)
//            Field filed = t.getClass().getDeclaredField(variable);
//            filed.setAccessible(true);
//            return filed.get(t).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
