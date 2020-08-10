package com.ljryh.client.controller.shiro;

import com.ljryh.client.entity.shiro.LoginDTO;
import com.ljryh.client.entity.shiro.User;
import com.ljryh.client.service.shiro.ShiroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Api(tags = "Shiro权限管理")
@RestController
public class ShiroController {

    @Resource
    private ShiroService shiroService;


    /**
     * 登录
     */
    @ApiOperation(value = "登陆", notes = "参数:用户名 密码")
    @PostMapping("/sys/login")
    public Map<String, Object> login(@RequestBody @Validated LoginDTO loginDTO, BindingResult bindingResult) {
        Map<String, Object> result = new ConcurrentHashMap<>();
        if (bindingResult.hasErrors()) {
            result.put("status", 400);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
            return result;
        }

        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        //用户信息
        User user = shiroService.findByUsername(username);
        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(password)) {
            result.put("status", 400);
            result.put("msg", "账号或密码有误");
        } else {
            //生成token，并保存到数据库
            result = shiroService.createToken(user.getUserId());
            result.put("status", 200);
            result.put("msg", "登陆成功");
        }
        return result;
    }

    /**
     * 退出
     */
    @ApiOperation(value = "登出", notes = "参数:token")
    @PostMapping("/sys/logout")
    public Map<String, Object> logout(@RequestHeader("token")String token) {
        Map<String, Object> result = new ConcurrentHashMap<>();
        shiroService.logout(token);
        result.put("status", 200);
        result.put("msg", "您已安全退出系统");
        return result;
    }

}
