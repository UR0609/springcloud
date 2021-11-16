package com.ljryh.client.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljryh.client.entity.Order;
import com.ljryh.client.service.IOrderService;
import com.ljryh.common.entity.CallResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ljryh
 * @date 2021/10/25 16:25
 */
@RestController
@RequestMapping("/emp/order")
public class OrderController {

    @Resource
    private IOrderService orderService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object findAll(@RequestBody Order entity) {
        IPage<Order> page = new Page<>(entity.getPageNo(), entity.getPageSize());
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.setEntity(entity);
        return CallResult.success(orderService.page(page, wrapper));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody Order entity) {
        boolean judge = orderService.save(entity);
        return judge ? CallResult.success() : CallResult.fail();
    }

    @RequestMapping(value = "/mod", method = RequestMethod.POST)
    public Object mod(@RequestBody Order entity) {
        boolean judge = orderService.updateById(entity);
        return judge ? CallResult.success() : CallResult.fail();
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Object del(@RequestBody Order entity) {
        boolean judge = orderService.removeById(entity.getId());
        return judge ? CallResult.success() : CallResult.fail();
    }

    @RequestMapping(value = "/sel", method = RequestMethod.POST)
    public Object sel(@RequestBody Order entity) {
        Order result = orderService.getById(entity.getId());
        return result != null ? CallResult.success(result) : CallResult.fail();
    }

}
