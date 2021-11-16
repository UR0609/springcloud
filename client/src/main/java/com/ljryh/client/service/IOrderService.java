package com.ljryh.client.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljryh.client.entity.Order;

/**
 * @author ljryh
 * @date 2021/10/25 16:26
 */
public interface IOrderService extends IService<Order> {

    IPage<Order> page(IPage<Order> page, QueryWrapper<Order> wrapper);

    boolean save(Order entity);

    boolean updateById(Order entity);

    boolean removeById(Long id);

    Order getById(Long id);

}
