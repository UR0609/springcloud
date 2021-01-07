package com.ljryh.client;

import com.ljryh.client.entity.SMenu;
import com.ljryh.client.service.ISMenuService;
import com.ljryh.client.utils.RedisUtils;
import com.ljryh.client.utils.TreeUtils;
import com.ljryh.common.utils.JacksonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ISMenuService menuService;

    @Test
    public void testSave1() throws Exception {

        List<SMenu> list = menuService.list();
        TreeUtils menuTree  =new TreeUtils(list);
        list=menuTree.builTree();
        System.out.println(JacksonUtil.entityToJson(list));
//        for (int i = 0; i < 5; i++) {
//            Children children = new Children();
//            children.setId((long) i);
//            children.setName(i+"");
//            redisUtils.lSet("children", GsonUtil.ModuleTojosn(children));
//        }
//        redisUtils.del("children");
//        redisUtils.likeDel("TID74");
//        redisUtils.likeDel("process:role");

    }

}
