package com.ljryh.client.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * @author ljryh
 * @date 2021/3/3 10:06
 */
@Slf4j
@Service
public class TaskExecutor {

    @Async("asyncServiceExecutor")
    public Future<String> doTask1() throws InterruptedException{
        log.info("Task1 started.");
        long start = System.currentTimeMillis();
        Thread.sleep(5000);
        long end = System.currentTimeMillis();

        log.info("Task1 finished, time elapsed: {} ms.", end-start);

        return new AsyncResult<>("Task1 accomplished!");
    }

    @Async("customServiceExecutor")
    public Future<String> doTask2() throws InterruptedException{
        log.info("Task2 started.");
        long start = System.currentTimeMillis();
        Thread.sleep(3000);
        long end = System.currentTimeMillis();

        log.info("Task2 finished, time elapsed: {} ms.", end-start);

        return new AsyncResult<>("Task2 accomplished!");
    }

    @Async("asyncServiceExecutor")
    public Future<String> doTask1(CountDownLatch latch) throws InterruptedException{
        log.info("Task1 started.");
        long start = System.currentTimeMillis();
        Thread.sleep(5000);
        long end = System.currentTimeMillis();

        log.info("Task1 finished, time elapsed: {} ms.", end-start);
        latch.countDown();
        return new AsyncResult<>("Task1 accomplished!");
    }

    @Async("customServiceExecutor")
    public Future<String> doTask2(CountDownLatch latch) throws InterruptedException{
        log.info("Task2 started.");
        long start = System.currentTimeMillis();
        Thread.sleep(3000);
        long end = System.currentTimeMillis();

        log.info("Task2 finished, time elapsed: {} ms.", end-start);
        latch.countDown();
        return new AsyncResult<>("Task2 accomplished!");
    }

}
