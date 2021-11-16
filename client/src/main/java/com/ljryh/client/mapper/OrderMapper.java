package com.ljryh.client.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljryh.client.entity.Order;
import org.apache.ibatis.annotations.Param;

/**
 * @author ljryh
 * @date 2021/10/25 16:28
 */
public interface OrderMapper extends BaseMapper<Order> {
    IPage<Order> selectPage(IPage<Order> page, @Param("ew") QueryWrapper<Order> wrapper);

    int insert(Order entity);

    int updateById(Order entity);

    int deleteById(@Param("id") Long id);

    Order selectById(@Param("id") Long id);
}
