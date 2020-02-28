package com.ljryh.ribbon.service;

import com.alibaba.fastjson.JSON;
import com.ljryh.ribbon.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class RibbonService {
    @Resource
    private RestTemplate restTemplate;

//    @HystrixCommand(fallbackMethod = "hiError")
    public String get(String data) {
        return restTemplate.getForObject("http://CLIENT/getRibbon?data=" + data, String.class);
    }

//    @HystrixCommand(fallbackMethod = "hiError")
    public String post() {
        User user = new User();
        user.setId(1l);
        user.setAge(25);
        user.setName("ljryh");
        user.setEmail("124732719@qq.com");
        String data = JSON.toJSONString(user);
        return restTemplate.postForObject("http://CLIENT/postRibbon", data, String.class);
    }

//    public String hiError(String data) {
//        return "hi," + data + ",sorry,error!";
//    }

}
