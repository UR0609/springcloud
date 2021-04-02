package com.ljryh.client;

import com.ljryh.client.mapper.ChildrenMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbTest {

    @Resource
    private ChildrenMapper childrenMapper;

    @Test
    public void testSave1() {
//        MongodbTestModel mo = new MongodbTestModel();
//        for (int i = 0; i < 100; i++) {
//            mo.setMid(i + "");
//            mo.setName("MongodbTestModel" + i);
//            mo.setAge("22");
//            MongodbUtils.save(mo);
//        }
//
//        Map<String,Object> map = new ConcurrentHashMap<>();
//
//        map.put("age","22");
//
//        List<MongodbTestModel> list =  MongodbUtils.findAll(MongodbTestModel.class,"mongodbTestModel");
//
//
//        System.out.println(123);

//        List<Map> list =  MongodbUtils.findAll(Map.class,"MD_cmbcdc_cmbc");
//        Map<String, Object> map = new ConcurrentHashMap<>();
//        map.put("BatchNo","20210222007");
//        List<Map> list1 =  MongodbUtils.find(map,Map.class,"MD_cmbcdc_cmbc");
//        map.put("BatchNo","20210222008");
//        List<Map> list2 =  MongodbUtils.find(map,Map.class,"MD_cmbcdc_cmbc");
//
//        System.out.println(list.size());

        Map<String,Object> map = new ConcurrentHashMap<>();
        map.put("name","a");
//        childrenMapper.updateEntityByMap(map);

//        childrenMapper.updateTest(map);
        childrenMapper.updateTest2(map);

        System.out.println(123);
    }

}
