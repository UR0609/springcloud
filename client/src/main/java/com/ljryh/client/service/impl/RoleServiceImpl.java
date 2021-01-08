package com.ljryh.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljryh.client.entity.Role;
import com.ljryh.client.mapper.RoleMapper;
import com.ljryh.client.service.IRoleService;
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
 *  服务实现类
 * </p>
 *
 * @author ljryh
 * @since 2020-08-12
 */
@Slf4j
@Service
@Transactional(rollbackFor=Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {


    @Override
    @Cacheable(value = "cache:roleList", key = "'role-' + #page.current+'-'+#page.size+'-'+#wrapper.entity.roleName+'-'+#wrapper.entity.remarks")
    public IPage<Role> page(@Param("page") IPage<Role> page, @Param("ew") QueryWrapper<Role> wrapper) {
        return this.baseMapper.selectPage(page, wrapper);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "cache:roleList", allEntries = true),
            @CacheEvict(cacheNames = "cache:user", key = "'id-' + #id"),
            @CacheEvict(cacheNames = "cache:shiro:role", allEntries = true)
    })
    public boolean save(Role entity) {
        int result = this.baseMapper.insert(entity);
        log.info("<--------------------------插入数据 id : {}------------------------------>",entity.getId());
        return result == 1;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "cache:roleList", allEntries = true),
            @CacheEvict(cacheNames = "cache:user", key = "'id-' + #id"),
            @CacheEvict(cacheNames = "cache:shiro:role", allEntries = true)
    })
    public boolean updateById(Role entity) {
        int result = this.baseMapper.updateById(entity);
        log.info("<--------------------------更新数据 id : {}------------------------------>",entity.getId());
        return result == 1;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "cache:roleList", allEntries = true),
            @CacheEvict(cacheNames = "cache:user", key = "'id-' + #id"),
    })
    public boolean removeById(Serializable id) {
        int result = this.baseMapper.deleteById(id);
        log.info("<--------------------------清除缓存 id : {}------------------------------>",id);
        return result == 1;
    }

    @Override
    @Cacheable(cacheNames = "cache:role", key = "'id-' + #id")
    public Role getById(Serializable id) {
        Role role = this.baseMapper.selectById(id);
        return role;
    }
}
