package com.ljryh.client.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljryh.client.entity.Role;
import com.ljryh.client.service.IRoleService;
import com.ljryh.common.entity.CallResult;
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
 * @since 2020-08-12
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Object findAll(Role entity/*, HttpServletRequest request*/){
        //获取前台发送过来的数据
//        Integer pageNo = Integer.valueOf(request.getParameter("pageNo"));
//        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        IPage<Role> page = new Page<>(entity.getPageNo(), entity.getPageSize());
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.setEntity(entity);
        return roleService.page(page,wrapper);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object add(@RequestBody Role entity){
        boolean judge = roleService.save(entity);
        if (judge) {
            return CallResult.success();
        } else {
            return CallResult.fail();
        }
    }

    @RequestMapping(value = "/mod",method = RequestMethod.POST)
    public Object mod(@RequestBody Role entity){
        boolean judge = roleService.updateById(entity);
        if (judge) {
            return CallResult.success();
        } else {
            return CallResult.fail();
        }
    }

    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public Object del(@RequestBody Role entity){
        boolean judge = roleService.removeById(entity.getId());
        if (judge) {
            return CallResult.success();
        } else {
            return CallResult.fail();
        }
    }

    @RequestMapping(value = "/sel",method = RequestMethod.POST)
    public Object sel(@RequestBody Role entity){
        Role result = roleService.getById(entity.getId());
        if (result != null) {
            return CallResult.success(result);
        } else {
            return CallResult.fail();
        }
    }

}
