package com.ljryh.client.config.factory;

/**
 * @author ljryh
 * @version 1.0
 * @date 2024/9/4 10:13
 */
public abstract class UnifiedBehavior {

    private final String typeEnum;

    protected UnifiedBehavior(String typeEnum) {
        this.typeEnum = typeEnum;
    }

    public void method(String name) {
        doSomething(name);
    }

    public abstract void doSomething(String name);

    public String getTypeEnum() {
        return typeEnum;
    }
}
