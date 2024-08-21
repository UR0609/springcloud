package com.ljryh.client;

import net.hasor.spring.boot.EnableHasor;
import net.hasor.spring.boot.EnableHasorWeb;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RestController;
/**
 * https://github.com.ipaddress.com/
 * 获取 github.com
 * https://fastly.net.ipaddress.com/github.global.ssl.fastly.net#ipinfo
 * 获取 github.global.ssl.fastly.net
 * https://github.com.ipaddress.com/assets-cdn.github.com
 * 获取 assets-cdn.github.com
 *
 * sudo vi /etc/hosts
 *
 * 140.82.112.3 github.com
 * 151.101.1.6 github.global.ssl.fastly.net
 * 151.101.65.6 github.global.ssl.fastly.net
 * 151.101.129.6 github.global.ssl.fastly.net
 * 151.101.193.6 github.global.ssl.fastly.net
 * 140.82.112.3 assets-cdn.github.com
 *
 * sudo killall -HUP mDNSResponder
 */
// git config --global http.sslVerify "false"
// git config --global http.version HTTP/1.1
// 取消全局代理：
//git config --global --unset http.proxy
//git config --global --unset https.proxy
// netstat -aon|findstr "8661"
// 自动化接口生成
@EnableHasor()
@EnableHasorWeb()
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.ljryh.client.mapper")
@RestController
public class ClientApplication {

//    @Resource
//    private RedisUtils redisUtils;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);

    }

//    @Resource
//    private DelaySender sender;
////	@Autowired
////	private QueueSender sender;
//
//    @RequestMapping("/send")
//    public String helloworld(String data) {
//        return sender.sendDelay(data);
//    }
//
//    @Value("${server.port}")
//    private String port;
//
//    @RequestMapping("/getRibbon")
//    public String getRibbon(@RequestParam String data, HttpServletRequest request, HttpServletResponse response) {
//
//        redisUtils.set("test","test");
//        System.out.println(redisUtils.get("test"));
//
//        String headValue = request.getHeader("X-Request-Id");//获取单个请求头name对应的value值
//        System.out.println(headValue);
//
//        return "data: " + data + ",i am from port:" + port;
//    }
//
//    @RequestMapping(value = "/postRibbon", method = RequestMethod.POST)
//    public String postRibbon(@RequestBody String data) {
////        String json = JSON.toJSONString(user);
//        return "SUCCESS:"+data;
//    }

}
