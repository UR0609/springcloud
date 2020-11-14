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



//        for (int i = 0; i < 5; i++) {
//            Children children = new Children();
//            children.setId((long) i);
//            children.setName(i+"");
//            redisUtils.lSet("children", GsonUtil.ModuleTojosn(children));
//        }
//        redisUtils.del("children");
//        redisUtils.likeDel("process:role");

    }

}
