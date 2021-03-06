package com.ljryh.client;

import com.ljryh.client.config.queue.DelaySender;
import com.ljryh.client.utils.RedisUtils;
import net.hasor.spring.boot.EnableHasor;
import net.hasor.spring.boot.EnableHasorWeb;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

// 自动化接口生成
@EnableHasor()
@EnableHasorWeb()
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.ljryh.client.mapper")
@RestController
public class ClientApplication {

    @Resource
    private RedisUtils redisUtils;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);

    }

    @Resource
    private DelaySender sender;
//	@Autowired
//	private QueueSender sender;

    @RequestMapping("/send")
    public String helloworld(String data) {
        return sender.sendDelay(data);
    }

    @Value("${server.port}")
    private String port;

    @RequestMapping("/getRibbon")
    public String getRibbon(@RequestParam String data) {

        redisUtils.set("test","test");
        System.out.println(redisUtils.get("test"));

        return "data: " + data + ",i am from port:" + port;
    }

    @RequestMapping(value = "/postRibbon", method = RequestMethod.POST)
    public String postRibbon(@RequestBody String data) {
//        String json = JSON.toJSONString(user);
        return "SUCCESS:"+data;
    }

}
