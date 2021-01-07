package com.ljryh.client.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljryh.client.entity.SMenu;
import com.ljryh.client.service.ISMenuService;
import com.ljryh.common.entity.CallResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @RequestMapping(value = "/sysmenu", method = RequestMethod.GET)
    public Object findMenu(@RequestHeader("token") String token) {
//        String result = "[{\"id\":2,\"path\":\"/home\",\"component\":\"Home\",\"name\":\"人员管理\",\"iconCls\":\"fa fa-user-circle-o\",\"children\":[{\"id\":null,\"path\":\"/emp/basic\",\"component\":\"EmpBasic\",\"name\":\"基本资料\",\"iconCls\":null,\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}}],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}},{\"id\":5,\"path\":\"/home\",\"component\":\"Home\",\"name\":\"统计管理\",\"iconCls\":\"fa fa-bar-chart\",\"children\":[{\"id\":null,\"path\":\"/sta/all\",\"component\":\"StaAll\",\"name\":\"综合信息统计\",\"iconCls\":null,\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}},{\"id\":null,\"path\":\"/sta/pers\",\"component\":\"StaPers\",\"name\":\"人事信息统计\",\"iconCls\":null,\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}}],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}},{\"id\":6,\"path\":\"/home\",\"component\":\"Home\",\"name\":\"系统管理\",\"iconCls\":\"fa fa-windows\",\"children\":[{\"id\":null,\"path\":\"/sys/basic\",\"component\":\"SysBasic\",\"name\":\"基础设置\",\"iconCls\":null,\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}},{\"id\":null,\"path\":\"/sys/log\",\"component\":\"SysLog\",\"name\":\"日志管理\",\"iconCls\":null,\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}},{\"id\":null,\"path\":\"/sys/user\",\"component\":\"user/SysUser\",\"name\":\"用户管理\",\"iconCls\":null,\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}},{\"id\":null,\"path\":\"/sys/role\",\"component\":\"role/SysRole\",\"name\":\"权限管理\",\"iconCls\":null,\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}},{\"id\":null,\"path\":\"/sys/menu\",\"component\":\"meny/SysMenu\",\"name\":\"菜单管理\",\"iconCls\":null,\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}}],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}}]";
        String result = "[{\"id\":2,\"path\":\"/home\",\"component\":\"Home\",\"name\":\"人员管理\",\"iconCls\":\"fa fa-user-circle-o\",\"children\":[{\"id\":null,\"path\":\"/emp/basic\",\"component\":\"EmpBasic\",\"name\":\"基本资料\",\"iconCls\":null,\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}}],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}},{\"id\":5,\"path\":\"/home\",\"component\":\"Home\",\"name\":\"统计管理\",\"iconCls\":\"fa fa-bar-chart\",\"children\":[{\"id\":null,\"path\":\"/sta/all\",\"component\":\"StaAll\",\"name\":\"综合信息统计\",\"iconCls\":null,\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}},{\"id\":null,\"path\":\"/sta/pers\",\"component\":\"StaPers\",\"name\":\"人事信息统计\",\"iconCls\":null,\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}}],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}},{\"id\":6,\"path\":\"/home\",\"component\":\"Home\",\"name\":\"系统管理\",\"iconCls\":\"fa fa-windows\",\"children\":[{\"id\":null,\"path\":\"/sys/user\",\"component\":\"SysUser/user\",\"name\":\"用户管理\",\"iconCls\":\"el-icon-user-solid\",\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}},{\"id\":null,\"path\":\"/sys/role\",\"component\":\"SysRole/role\",\"name\":\"权限管理\",\"iconCls\":null,\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}},{\"id\":null,\"path\":\"/sys/menu\",\"component\":\"SysMenu/menu\",\"name\":\"菜单管理\",\"iconCls\":null,\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}}],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}}]";
        return result;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object findAll(@RequestBody SMenu entity/*, HttpServletRequest request*/) {
//        IPage<SMenu> page = new Page<>(entity.getPageNo(), entity.getPageSize());
        QueryWrapper<SMenu> wrapper = new QueryWrapper<>();
        wrapper.setEntity(entity);
        List<SMenu> list = menuService.list(wrapper);
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

    @RequestMapping(value = "/selectTree", method = RequestMethod.POST)
    public Object selectTree(@RequestBody SMenu entity/*, HttpServletRequest request*/) {
//        IPage<SMenu> page = new Page<>(entity.getPageNo(), entity.getPageSize());
        QueryWrapper<SMenu> wrapper = new QueryWrapper<>();
        wrapper.setEntity(entity);
        List<SMenu> list = menuService.selectTree(wrapper);
        return CallResult.success(list);
    }

}
