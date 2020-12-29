package com.ljryh.client;

import com.ljryh.client.utils.MongodbUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbTest {

    @Test
    public void testSave1() {
        MongodbTestModel mo = new MongodbTestModel();
        for (int i = 0; i < 100; i++) {
            mo.setMid(i + "");
            mo.setName("MongodbTestModel" + i);
            mo.setAge("22");
            MongodbUtils.save(mo);
        }

        Map<String,Object> map = new ConcurrentHashMap<>();

        map.put("age","22");

        List<MongodbTestModel> list =  MongodbUtils.findAll(MongodbTestModel.class,"mongodbTestModel");


        System.out.println(123);

    }

}
