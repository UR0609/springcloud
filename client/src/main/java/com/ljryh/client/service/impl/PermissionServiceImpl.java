package com.ljryh.client.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljryh.client.entity.Permission;
import com.ljryh.client.mapper.PermissionMapper;
import com.ljryh.client.service.IPermissionService;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Override
    @CacheEvict(cacheNames = "cache:shiro:role", allEntries = true)
    public boolean save(Permission entity) {
        int result = this.baseMapper.insert(entity);
        log.info("<--------------------------插入数据 id : {}------------------------------>",entity.getId());
        return result == 1;
    }

    @Override
    @CacheEvict(cacheNames = "cache:shiro:role", allEntries = true)
    public boolean updateById(Permission entity) {
        int result = this.baseMapper.updateById(entity);
        log.info("<--------------------------更新数据 id : {}------------------------------>",entity.getId());
        return result == 1;
    }

    @Override
    @CacheEvict(cacheNames = "cache:shiro:role", allEntries = true)
    public boolean removeById(Serializable id) {
        int result = this.baseMapper.deleteById(id);
        log.info("<--------------------------清除缓存 id : {}------------------------------>",id);
        return result == 1;
    }
}
