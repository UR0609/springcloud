package com.ljryh.common.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GsonUtils {
    private static Gson GsonUtilsGson = new Gson();

    /**
     * @param jsonString json字符串
     * @param cls        需要转化的类型
     * @param <T>        需要转化的类型
     * @return 返回实体对象
     * @fun 根据不同类型进行json到实体间的转化
     */
    public static <T> T josnToModule(String jsonString, Class<T> cls) {
        T list;
        list = GsonUtilsGson.fromJson(jsonString, cls);
        return list;
    }

    public static <T> T josnToModule(String jsonString, Type typeOfT) {

        // List<T> list = GsonUtils.josnToModule(jsonString, new TypeToken<List<T>>() {}.getType());

        T list;
        list = GsonUtilsGson.fromJson(jsonString, typeOfT);
        return list;
    }

    /**
     * @param cls 需要转化的类型
     * @param <T> 需要转化的类型
     * @return 返回Json字符串
     * @fun 根据不同类型进行实体到json间的转化
     */
    public static <T> String ModuleTojosn(T cls) {
        return GsonUtilsGson.toJson(cls);
    }
}
