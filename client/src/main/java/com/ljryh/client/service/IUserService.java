package com.ljryh.client.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljryh.client.entity.shiro.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljryh
 * @since 2020-02-13
 */
public interface IUserService extends IService<User> {

    IPage<User> page(IPage<User> page, QueryWrapper<User> wrapper);

    void test();
}
