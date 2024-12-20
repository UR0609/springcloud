package com.ljryh.client.config.factory;

/**
 * @author ljryh
 * @version 1.0
 * @date 2024/9/4 10:13
 */
public class SpecificTypeA extends UnifiedBehavior {

    public SpecificTypeA() {
        super("TYPE_A");
    }

    @Override
    public void doSomething(String name) {
        System.out.println(name);
    }
}
