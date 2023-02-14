package com.ljryh.client.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljryh.client.entity.SDatabase;
import com.ljryh.client.entity.shiro.User;
import com.ljryh.client.service.ISDatabaseService;
import com.ljryh.common.entity.CallResult;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 数据库生成 前端控制器
 * </p>
 *
 * @author ljryh
 * @since 2023-02-09
 */
@RestController
@RequestMapping("/sys/database")
public class SDatabaseController {

    @Resource
    private ISDatabaseService sDatabaseService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object findAll(@RequestBody SDatabase entity) {
        IPage<SDatabase> page = new Page<>(entity.getPageNo(), entity.getPageSize());
        QueryWrapper<SDatabase> wrapper = new QueryWrapper<>();
        wrapper.setEntity(entity);
        return CallResult.success(sDatabaseService.page(page, wrapper));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody SDatabase entity) {
        // 获取当前登录人id
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        entity.setCreater(user.getId());
        // 添加创建时间
        entity.setCreateTime(LocalDateTime.now());
        boolean judge = sDatabaseService.save(entity);
        return judge ? CallResult.success() : CallResult.fail();
    }

    @RequestMapping(value = "/mod", method = RequestMethod.POST)
    public Object mod(@RequestBody SDatabase entity) {
        // 获取当前登录人id
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        entity.setUpdater(user.getId());
        // 添加创建时间
        entity.setUpdateTime(LocalDateTime.now());
        boolean judge = sDatabaseService.updateById(entity);
        return judge ? CallResult.success() : CallResult.fail();
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Object del(@RequestBody SDatabase entity) {
        boolean judge = sDatabaseService.removeById(entity.getId());
        return judge ? CallResult.success() : CallResult.fail();
    }

    @RequestMapping(value = "/sel", method = RequestMethod.POST)
    public Object sel(@RequestBody SDatabase entity) {
        SDatabase result = sDatabaseService.getById(entity.getId());
        return result != null ? CallResult.success(result) : CallResult.fail();
    }

}
