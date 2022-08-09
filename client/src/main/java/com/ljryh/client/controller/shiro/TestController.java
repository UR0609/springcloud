package com.ljryh.client.controller.shiro;

import com.ljryh.client.entity.shiro.User;
import com.ljryh.client.utils.annotation.OperLog;
import com.ljryh.common.utils.GsonUtils;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Api(tags = "测试")
@RequestMapping("/test")
public class TestController {
    @RequiresPermissions({"/emp/test/save"}) //没有的话 AuthorizationException
    @PostMapping("/save")
    @OperLog(operModul = "保存功能", operType = "save", operDesc = "保存测试")
    public Map<String, Object> save(@RequestHeader("token") String token,@RequestBody String path) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        System.out.println(GsonUtils.ModuleTojosn(user));
        System.out.println("save");
        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("status", 200);
        map.put("msg", "当前用户有save的权力");
        return map;
    }//f603cd4348b8f1d41226e3f555d392bd

    @RequiresPermissions({"/emp/test/delete"}) //没有的话 AuthorizationException
    @DeleteMapping("/delete")
    public Map<String, Object> delete(@RequestHeader("token") String token) {
        System.out.println("delete");
        Map<String, Object> map = new ConcurrentHashMap<String, Object>();
        map.put("status", 200);
        map.put("msg", "当前用户有delete的权力");
        return map;
    }

    @RequiresPermissions({"/emp/test/update"}) //没有的话 AuthorizationException
    @PutMapping("update")
    public Map<String, Object> update(@RequestHeader("token") String token) {
        System.out.println("update");
        Map<String, Object> map = new ConcurrentHashMap<String, Object>();
        map.put("status", 200);
        map.put("msg", "当前用户有update的权力");
        return map;
    }

    @RequiresPermissions({"/emp/test/select"}) //没有的话 AuthorizationException
    @GetMapping("select")
    public Map<String, Object> select(@RequestHeader("token") String token) {
        System.out.println("select");
        Map<String, Object> map = new ConcurrentHashMap<String, Object>();
        map.put("status", 200);
        map.put("msg", "当前用户有select的权力");
        return map;
    }

    @RequiresRoles({"vip"}) //没有的话 AuthorizationException
    @GetMapping("/vip")
    public Map<String, Object> vip(@RequestHeader("token") String token) {
        System.out.println("vip");
        Map<String, Object> map = new ConcurrentHashMap<String, Object>();
        map.put("status", 200);
        map.put("msg", "当前用户有VIP角色");
        return map;
    }

    @RequiresRoles({"svip"}) //没有的话 AuthorizationException
    @GetMapping("/svip")
    public Map<String, Object> svip(@RequestHeader("token") String token) {
        System.out.println("svip");
        Map<String, Object> map = new ConcurrentHashMap<String, Object>();
        map.put("status", 200);
        map.put("msg", "当前用户有SVIP角色");
        return map;
    }

    @RequiresRoles({"p"}) //没有的话 AuthorizationException
    @GetMapping("/p")
    public Map<String, Object> p(@RequestHeader("token") String token) {
        System.out.println("p");
        Map<String, Object> map = new ConcurrentHashMap<String, Object>();
        map.put("status", 200);
        map.put("msg", "当前用户有P角色");
        return map;
    }
}
