package com.ljryh.client;

import com.ljryh.client.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisUtils redisUtils;

    @Test
    public void testSave1() {

//        redisUtils.set("process:role:1","1");
//        redisUtils.set("process:role:2","2");
//        redisUtils.set("process:role:3","3");

        redisUtils.likeDel("process:role");

    }

}
