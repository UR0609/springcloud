package com.ljryh.client.controller;

import com.ljryh.client.task.TaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author ljryh
 * @date 2021/3/3 10:07
 */
@Slf4j
@RestController
@RequestMapping("/task")
public class TaskController {

    @Resource
    private TaskExecutor serviceImpl;

    @RequestMapping("/helloFuture")
    @ResponseBody
    public String helloFuture() {
        try {
            Future<String> future1 = serviceImpl.doTask1();
            Future<String> future2 = serviceImpl.doTask2();
            //自旋锁，停止等待
            while (true) {
                if (future1.isDone() && future2.isDone()) {
                    log.info("Task1 result:{}", future1.get());
                    log.info("Task2 result:{}", future2.get());
                    break;
                }
                Thread.sleep(1000);
            }
            log.info("All tasks finished.");
            return "S";
        } catch (InterruptedException e) {
            log.error("错误信息1", e);
            return "F";
        } catch (ExecutionException e) {
            log.error("错误信息2", e);
            return "F";
        }
    }

    @RequestMapping("/helloFuture2")
    @ResponseBody
    public String helloFuture2() {
        try {
            CountDownLatch latch=new CountDownLatch(2);
            Future<String> future1 = serviceImpl.doTask1(latch);
            Future<String> future2 = serviceImpl.doTask2(latch);
            //等待两个线程执行完毕
            latch.await();
            log.info("All tasks finished!");
            String result1 = future1.get();
            String result2 = future2.get();
            log.info(result1+"--"+result2);
            return "S";
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "F";
    }

}
