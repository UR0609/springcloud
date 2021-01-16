package com.ljryh.client.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljryh.client.entity.Permission;
import com.ljryh.client.entity.SMenu;
import com.ljryh.client.entity.shiro.User;
import com.ljryh.client.service.IPermissionService;
import com.ljryh.client.service.ISMenuService;
import com.ljryh.common.entity.CallResult;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ljryh
 * @since 2021-01-06
 */
@RestController
@RequestMapping("/sys/menu")
public class SMenuController {

    @Resource
    private ISMenuService menuService;
    @Resource
    private IPermissionService permissionService;

    /**
     * 菜单列表
     *
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sysmenu", method = RequestMethod.GET)
    public Object findMenu(@RequestHeader("token") String token) throws Exception {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<SMenu> list = menuService.list(user.getId());
        return list;

    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object findAll(@RequestBody SMenu entity/*, HttpServletRequest request*/) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        entity.setId(user.getId());
//        IPage<SMenu> page = new Page<>(entity.getPageNo(), entity.getPageSize());
        QueryWrapper<SMenu> wrapper = new QueryWrapper<>();
        wrapper.setEntity(entity);
        List<SMenu> list = menuService.list(user.getId());
        return CallResult.success(list);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody SMenu entity) {
        boolean judge = menuService.save(entity);
        if (judge) {
            return CallResult.success();
        } else {
            return CallResult.fail();
        }
    }

    @RequestMapping(value = "/mod", method = RequestMethod.POST)
    public Object mod(@RequestBody SMenu entity) {
        boolean judge = menuService.updateById(entity);
        if (judge) {
            return CallResult.success();
        } else {
            return CallResult.fail();
        }
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Object del(@RequestBody SMenu entity) {
        boolean judge = menuService.removeById(entity.getId());
        if (judge) {
            return CallResult.success();
        } else {
            return CallResult.fail();
        }
    }

    @RequestMapping(value = "/sel", method = RequestMethod.POST)
    public Object sel(@RequestBody SMenu entity) {
        SMenu result = menuService.getById(entity.getId());
        if (result != null) {
            return CallResult.success(result);
        } else {
            return CallResult.fail();
        }
    }

    /**
     * 下拉树形
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/selectTree", method = RequestMethod.POST)
    public Object selectTree(@RequestBody SMenu entity/*, HttpServletRequest request*/) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<SMenu> list = menuService.selectTree(user.getId());
        return CallResult.success(list);
    }

    @RequestMapping(value = "/selAllPermission", method = RequestMethod.POST)
    public Object selAllPermission(@RequestBody SMenu entity) {

        Map<String, Object> map = new ConcurrentHashMap<>();

        List<Permission> list = permissionService.list();
        map.put("permission", list);
        if (list.size() != 0) {
            List<SMenu> nameList = menuService.getPermissionNameByMenuId(entity);
            if (nameList.size() != 0)
                map.put("list", nameList);
            return CallResult.success(map);
        } else {
            return CallResult.fail("查询数据失败！");
        }
    }

    @RequestMapping(value = "/bind",method = RequestMethod.POST)
    public Object bind(@RequestBody SMenu menu) {
        boolean judge = menuService.bind(menu);
//        judge = true;
        if (judge) {
            return CallResult.success("绑定成功");
        } else {
            return CallResult.fail("绑定失败");
        }
    }

}
