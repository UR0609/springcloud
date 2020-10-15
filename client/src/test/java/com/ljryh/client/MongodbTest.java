package com.ljryh.client;

import com.ljryh.client.utils.MongodbUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbTest {

    @Test
    public void testSave1() {
        MongodbTestModel mo = new MongodbTestModel();
        mo.setMid("123");
        mo.setName("MongodbTestModel");
        mo.setAge("22");
        MongodbUtils.save(mo);
    }

}
