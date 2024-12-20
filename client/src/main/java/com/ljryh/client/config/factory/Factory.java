package com.ljryh.client.config.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ljryh
 * @version 1.0
 * @date 2024/9/4 10:13
 */
public class Factory {
    private static final List<UnifiedBehavior> types = new ArrayList<>();

    private static Map<String, UnifiedBehavior> map;

    private static boolean init = false;

    public static void init() {
        types.add(new SpecificTypeA());
        types.add(new SpecificHuanBao2024());
//        types.add(new SpecificTypeC());
//        types.add(new SpecificTypeD());
        map = types.stream().collect(Collectors.toMap(UnifiedBehavior::getTypeEnum, Function.identity()));
        init = true;
    }

    public static UnifiedBehavior getInstance(String typeEnum) {
        if (!init) {
            init();
        }
        return map.get(typeEnum);
    }

    public static void main(String[] args) {
        UnifiedBehavior instance = Factory.getInstance("huanbao2024");
        instance.method("abc");
    }
}
