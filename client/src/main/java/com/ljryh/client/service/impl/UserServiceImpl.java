package com.ljryh.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljryh.client.entity.shiro.User;
import com.ljryh.client.mapper.shiro.UserMapper;
import com.ljryh.client.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ljryh
 * @since 2020-02-13
 */
@Slf4j
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    @Cacheable(value = "cache:userList", key = "'user-' + #page.current+'_'+#page.size")
    public IPage<User> page(@Param("page") IPage<User> page, @Param("ew") QueryWrapper<User> wrapper) {
        log.info("<--------------------------查询数据List  : 第{}页，{}条数据------------------------------>",page.getCurrent(),page.getSize());
        return this.baseMapper.selectPage(page, wrapper);
    }

    @Override
    @CacheEvict(cacheNames = "cache:userList", allEntries = true)
//    @Cacheable(cacheNames = "cache:user", key = "'id-' + #user.id")
    public boolean save(User user) {
        int result = this.baseMapper.insert(user);
        log.info("<--------------------------插入数据 id : {}------------------------------>",user.getId());
        if (result != 1)
            return false;
        else
            return true;
    }

    @Override
    @CacheEvict(cacheNames = "cache:userList", allEntries = true)
//    @CachePut(cacheNames = "cache:user", key = "'id-' + #user.id")
    public boolean updateById(User user) {
        int result = this.baseMapper.updateById(user);
        log.info("<--------------------------更新数据 id : {}------------------------------>",user.getId());
        if (result != 1)
            return false;
        else
            return true;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "cache:userList", allEntries = true),
            @CacheEvict(cacheNames = "cache:user", key = "'id-' + #id")
    })
    public boolean removeById(Serializable id) {
        int result = this.baseMapper.deleteById(id);
        log.info("<--------------------------清除缓存 id : {}------------------------------>",id);
        if (result != 1)
            return false;
        else
            return true;
    }

    @Override
    @Cacheable(cacheNames = "cache:user", key = "'id-' + #id")
    public User getById(Serializable id) {
        User result = this.baseMapper.selectById(id);
        log.info("<--------------------------没有走缓存 查询 id : {}------------------------------>",id);
        return result;
    }
}
