package com.ljryh.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljryh.client.entity.User;
import com.ljryh.client.mapper.UserMapper;
import com.ljryh.client.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ljryh
 * @since 2020-02-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    @Cacheable(value = "cache:user", key = "'user-' + #page.current+'_'+#page.size")
    public IPage<User> page(@Param("page") IPage<User> page, @Param("ew") QueryWrapper<User> wrapper) {
        return this.baseMapper.selectPage(page, wrapper);
    }

}
