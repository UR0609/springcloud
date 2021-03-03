package com.ljryh.client.config.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ljryh
 * @date 2021/3/3 10:01
 */
@Slf4j
public class PrintingPolicy implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.info(r.toString() + "被抛弃了！");
    }
}
