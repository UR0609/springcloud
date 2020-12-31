package com.ljryh.client.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljryh.client.entity.shiro.User;
import com.ljryh.client.service.IUserService;
import com.ljryh.common.entity.CallResult;
import com.ljryh.common.utils.GsonUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ljryh
 * @since 2020-02-13
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Resource
    private IUserService userService;

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Object findAll(@RequestBody User user/*, HttpServletRequest request*/){
        //获取前台发送过来的数据
//        Integer pageNo = Integer.valueOf(request.getParameter("pageNo"));
//        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        IPage<User> page = new Page<>(user.getPageNo(), user.getPageSize());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.setEntity(user);
        return CallResult.success(userService.page(page,wrapper));
    }

    @RequestMapping(value = "/exportExcel",method = RequestMethod.POST)
    public Object exportExcel(@RequestBody User user/*, HttpServletRequest request*/){
        //获取前台发送过来的数据
//        Integer pageNo = Integer.valueOf(request.getParameter("pageNo"));
//        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
//        IPage<User> page = new Page<>(user.getPageNo(), user.getPageSize());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.setEntity(user);
        return CallResult.success(userService.list(wrapper));
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object add(@RequestBody User user){
        boolean judge = userService.save(user);
        if (judge) {
            return CallResult.success("添加成功");
        } else {
            return CallResult.fail("添加失败");
        }
    }

    @RequestMapping(value = "/mod",method = RequestMethod.POST)
    public Object mod(@RequestBody User user){
        boolean judge = userService.updateById(user);
        if (judge) {
            return CallResult.success("修改成功");
        } else {
            return CallResult.fail("修改失败");
        }
    }

    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public Object del(@RequestBody User user){
        boolean judge = userService.removeById(user.getId());
        if (judge) {
            return CallResult.success("删除成功");
        } else {
            return CallResult.fail("删除失败");
        }
    }

    @RequestMapping(value = "/sel",method = RequestMethod.POST)
    public Object sel(@RequestBody User user){
        User result = userService.getById(user.getId());
        if (result != null) {
            return CallResult.success(result);
        } else {
            return CallResult.fail("查询数据失败！");
        }
    }

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public String hello(@RequestBody User user,String access_token) {
        int age = 15;
        userService.test();
        user.setAge(++age);
        System.out.println(access_token);
        System.out.println(GsonUtil.ModuleTojosn(user));

        return "wp.ljryh.com";
    }

}
