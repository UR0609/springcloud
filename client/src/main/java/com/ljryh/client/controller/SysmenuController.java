package com.ljryh.client.controller;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ljryh
 * @since 2020-08-12
 */
@RestController
@RequestMapping("/sys")
public class SysmenuController {

    @RequestMapping(value = "/sysmenu", method = RequestMethod.GET)
    public Object findAll(@RequestHeader("token") String token) {
        System.out.println(token);
        String result = "[{\"id\":2,\"path\":\"/home\",\"component\":\"Home\",\"name\":\"人员管理\",\"iconCls\":\"fa fa-user-circle-o\",\"children\":[{\"id\":null,\"path\":\"/emp/basic\",\"component\":\"EmpBasic\",\"name\":\"基本资料\",\"iconCls\":null,\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}}],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}},{\"id\":5,\"path\":\"/home\",\"component\":\"Home\",\"name\":\"统计管理\",\"iconCls\":\"fa fa-bar-chart\",\"children\":[{\"id\":null,\"path\":\"/sta/all\",\"component\":\"StaAll\",\"name\":\"综合信息统计\",\"iconCls\":null,\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}},{\"id\":null,\"path\":\"/sta/pers\",\"component\":\"StaPers\",\"name\":\"人事信息统计\",\"iconCls\":null,\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}}],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}},{\"id\":6,\"path\":\"/home\",\"component\":\"Home\",\"name\":\"系统管理\",\"iconCls\":\"fa fa-windows\",\"children\":[{\"id\":null,\"path\":\"/sys/basic\",\"component\":\"SysBasic\",\"name\":\"基础设置\",\"iconCls\":null,\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}},{\"id\":null,\"path\":\"/sys/log\",\"component\":\"SysLog\",\"name\":\"日志管理\",\"iconCls\":null,\"children\":[],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}}],\"meta\":{\"keepAlive\":false,\"requireAuth\":true}}]";
        return result;
    }

}
