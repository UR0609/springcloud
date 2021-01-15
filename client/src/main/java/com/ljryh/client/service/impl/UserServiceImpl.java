package com.ljryh.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljryh.client.entity.UserRole;
import com.ljryh.client.entity.shiro.User;
import com.ljryh.client.mapper.UserRoleMapper;
import com.ljryh.client.mapper.shiro.UserMapper;
import com.ljryh.client.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
@PropertySource("classpath:ljryh.properties")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Value("${task.replay.count}")
    private Integer taskReplayCount;

    @Override
    @Cacheable(value = "cache:userList", key = "'user-' + #page.current+'-'+#page.size+'-'+#wrapper.entity.name+'-'+#wrapper.entity.username")
    public IPage<User> page(@Param("page") IPage<User> page, @Param("ew") QueryWrapper<User> wrapper) {
        log.info("<--------------------------查询数据List  : 第{}页，{}条数据------------------------------>", page.getCurrent(), page.getSize());
        return this.baseMapper.selectPage(page, wrapper);
    }

    @Override
    public void test() {

    }

    @Override
    @CacheEvict(cacheNames = "cache:userList", allEntries = true)
//    @Cacheable(cacheNames = "cache:user", key = "'id-' + #user.id")
    public boolean save(User user) {
        int result = this.baseMapper.insert(user);
        log.info("<--------------------------插入数据 id : {}------------------------------>", user.getId());
        return result == 1;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "cache:userList", allEntries = true),
            @CacheEvict(cacheNames = "cache:shiro:user", allEntries = true)
    }/*,put = {
            @CachePut(cacheNames = "cache:user", key = "'id-' + #user.id"),
            @CachePut(cacheNames = "cache:shiro:user", key = "'id-' + #user.id"),
    }*/)
    @CachePut(cacheNames = "cache:user", key = "'id-' + #user.id")
    public boolean updateById(User user) {
        int result = this.baseMapper.updateById(user);
        log.info("<--------------------------更新数据 id : {}------------------------------>", user.getId());
        return result == 1;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "cache:userList", allEntries = true),
            @CacheEvict(cacheNames = "cache:user", key = "'id-' + #id"),
            @CacheEvict(cacheNames = "cache:shiro:user", allEntries = true)
    })
    public boolean removeById(Serializable id) {
        int result = this.baseMapper.deleteById(id);
        log.info("<--------------------------清除缓存 id : {}------------------------------>", id);
        return result == 1;
    }

    @Override
    @Cacheable(cacheNames = "cache:user", key = "'id-' + #id")
    public User getById(Serializable id) {
        User result = this.baseMapper.selectById(id);
        log.info("<--------------------------没有走缓存 查询 id : {}------------------------------>", id);
        return result;
    }

    @Override
    public Long getRoleIdByUserId(User user) {
        return this.baseMapper.getRoleIdByUserId(user);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "cache:shiro:user", allEntries = true)
    })
    public boolean bind(UserRole userRole) {
        UserRole data = new UserRole();
        data.setUserId(userRole.getUserId());
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(data);
        int count = userRoleMapper.selectCount(queryWrapper);

        int result;
        if(count == 0){
            result = userRoleMapper.insert(userRole);
        } else {
            result = userRoleMapper.update(userRole, queryWrapper);
        }
        return result == 1;
    }
}
