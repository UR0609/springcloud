package com.ljryh.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.ljryh.client.mapper")
@RestController
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Value("${server.port}")
    private String port;

    @RequestMapping("/getRibbon")
    public String getRibbon(@RequestParam String data) {
        return "data: " + data + ",i am from port:" + port;
    }

    @RequestMapping(value = "/postRibbon", method = RequestMethod.POST)
    public String postRibbon(@RequestBody String data) {
//        String json = JSON.toJSONString(user);
        return "SUCCESS:"+data;
    }

}
