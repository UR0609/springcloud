package com.ljryh.client.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljryh.client.entity.Role;
import com.ljryh.client.mapper.RoleMapper;
import com.ljryh.client.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
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
    @CacheEvict(cacheNames = "cache:shiro:role", allEntries = true)
    public boolean save(Role entity) {
        int result = this.baseMapper.insert(entity);
        log.info("<--------------------------插入数据 id : {}------------------------------>",entity.getId());
        if (result != 1)
            return false;
        else
            return true;
    }

    @Override
    @CacheEvict(cacheNames = "cache:shiro:role", allEntries = true)
    public boolean updateById(Role entity) {
        int result = this.baseMapper.updateById(entity);
        log.info("<--------------------------更新数据 id : {}------------------------------>",entity.getId());
        if (result != 1)
            return false;
        else
            return true;
    }

    @Override
    @CacheEvict(cacheNames = "cache:shiro:role", allEntries = true)
    public boolean removeById(Serializable id) {
        int result = this.baseMapper.deleteById(id);
        log.info("<--------------------------清除缓存 id : {}------------------------------>",id);
        if (result != 1)
            return false;
        else
            return true;
    }

}
