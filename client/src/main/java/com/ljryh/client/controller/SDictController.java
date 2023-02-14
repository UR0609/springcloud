package com.ljryh.client.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljryh.client.entity.SDict;
import com.ljryh.client.service.ISDictService;
import com.ljryh.common.entity.CallResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author ljryh
 * @since 2023-02-07
 */
@RestController
@RequestMapping("/sys/dict")
public class SDictController {
    
    @Resource
    private ISDictService sDictService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object findAll(@RequestBody SDict entity) {
        IPage<SDict> page = new Page<>(entity.getPageNo(), entity.getPageSize());
        QueryWrapper<SDict> wrapper = new QueryWrapper<>();
        wrapper.setEntity(entity);
        return CallResult.success(sDictService.page(page, wrapper));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody SDict entity) {
        boolean judge = sDictService.save(entity);
        return judge ? CallResult.success() : CallResult.fail();
    }

    @RequestMapping(value = "/mod", method = RequestMethod.POST)
    public Object mod(@RequestBody SDict entity) {
        boolean judge = sDictService.updateById(entity);
        return judge ? CallResult.success() : CallResult.fail();
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Object del(@RequestBody SDict entity) {
        boolean judge = sDictService.removeById(entity.getId());
        return judge ? CallResult.success() : CallResult.fail();
    }

    @RequestMapping(value = "/sel", method = RequestMethod.POST)
    public Object sel(@RequestBody SDict entity) {
        SDict result = sDictService.getById(entity.getId());
        return result != null ? CallResult.success(result) : CallResult.fail();
    }
    

}
