package com.ljryh.client.config.factory;

/**
 * @author ljryh
 * @version 1.0
 * @date 2024/9/4 10:13
 */
public class SpecificHuanBao2024 extends UnifiedBehavior {

    public SpecificHuanBao2024() {
        super("huanbao2024");
    }

    @Override
    public void doSomething(String name) {
        System.out.println("SpecificHuanBao2024:" + name);
    }
}
