package com.ljryh.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljryh.client.entity.Order;
import com.ljryh.client.mapper.OrderMapper;
import com.ljryh.client.service.IOrderService;
import org.springframework.stereotype.Service;

/**
 * @author ljryh
 * @date 2021/10/25 16:27
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Override
    public IPage<Order> page(IPage<Order> page, QueryWrapper<Order> wrapper) {
        return this.baseMapper.selectPage(page, wrapper);
    }

    @Override
    public boolean save(Order entity) {
        int result = this.baseMapper.insert(entity);
        return result == 1;
    }

    @Override
    public boolean updateById(Order entity) {
        int result = this.baseMapper.updateById(entity);
        return result == 1;
    }

    @Override
    public boolean removeById(Long id) {
        int result = this.baseMapper.deleteById(id);
        return result == 1;
    }

    @Override
    public Order getById(Long id) {
        return this.baseMapper.selectById(id);
    }
}
