package com.ljryh.client.service.shiro.impl;

import com.ljryh.client.config.auth.TokenGenerator;
import com.ljryh.client.entity.Permission;
import com.ljryh.client.entity.Role;
import com.ljryh.client.entity.shiro.SysToken;
import com.ljryh.client.entity.shiro.User;
import com.ljryh.client.mapper.PermissionMapper;
import com.ljryh.client.mapper.RoleMapper;
import com.ljryh.client.mapper.shiro.UserMapper;
import com.ljryh.client.service.shiro.ShiroService;
import com.ljryh.client.utils.RedisUtils;
import com.ljryh.common.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@Transactional(rollbackFor=Exception.class)
public class ShiroServiceImpl implements ShiroService {

    //12小时后失效
    private final static int EXPIRE = 12;


    @Resource
    private UserMapper userRepository;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 超时时间 5s
     */
    private static final int TIMEOUT = 5 * 1000;


    /**
     * 根据username查找用户
     *
     * @param username
     * @return User
     */
    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }


    @Override
    /**
     * 生成一个token
     *@param  [userId]
     *@return Result
     */
    public Map<String, Object> createToken(User user) {
        Map<String, Object> result = new ConcurrentHashMap<>();
        //生成一个token
        String token;
        //当前时间
        LocalDateTime now = LocalDateTime.now();
        //过期时间
        LocalDateTime expireTime = now.plusHours(EXPIRE);

        token = TokenGenerator.generateValue();
        redisUtils.set("shiro:user:" + user.getId(), token, 3600 * EXPIRE);

        SysToken sysToken = new SysToken();

        sysToken.setUserId(user.getId());
        sysToken.setToken(token);
        sysToken.setExpireTime(expireTime);

        try {
            redisUtils.set("shiro:token:" + token, JacksonUtils.objToJson(sysToken), 3600 * EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        //判断是否生成过token
//        SysToken tokenEntity = sysTokenRepository.findByUserId(user.getId());
//        if (tokenEntity == null) {
//            tokenEntity = new SysToken();
//            tokenEntity.setUserId(user.getId());
//            //保存token
//            tokenEntity.setToken(token);
//            tokenEntity.setUpdateTime(now);
//            tokenEntity.setExpireTime(expireTime);
//            sysTokenRepository.insert(tokenEntity);
//        } else {
//            QueryWrapper<SysToken> queryWrapper = new QueryWrapper<>();
//
//            queryWrapper.eq("user_id", tokenEntity.getUserId());
//
//            //更新token
//            tokenEntity.setToken(token);
//            tokenEntity.setUpdateTime(now);
//            tokenEntity.setExpireTime(expireTime);
//            sysTokenRepository.update(tokenEntity,queryWrapper);
//        }

        result.put("token", token);
        result.put("expire", expireTime);
        return result;
    }

    /**
     * 更新数据库的token，使前端拥有的token失效
     * 防止黑客利用token搞事情
     *
     * @param token
     */
    @Override
    public void logout(String token) {
//        SysToken byToken = findByToken(token);
//        //生成一个token
//        token = TokenGenerator.generateValue();
//        //修改token
//        byToken.setToken(token);
//        //使前端获取到的token失效
//        sysTokenRepository.insert(byToken);
        try {
            SysToken sysToken = JacksonUtils.jsonToPojo(redisUtils.get("shiro:token:" + token).toString(), SysToken.class);
            redisUtils.del("shiro:token:" + token);
            redisUtils.del("shiro:user:" + sysToken.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public SysToken findByToken(String accessToken) {

        if (redisUtils.get("shiro:token:" + accessToken) != null) {
            try {
                SysToken sysToken = JacksonUtils.jsonToPojo(redisUtils.get("shiro:token:" + accessToken).toString(), SysToken.class);
                return sysToken;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = "cache:shiro:user", key = "'id-' + #id")
    public User findByUserId(Long id) {
        return userRepository.findByUserId(id);
    }

    @Override
    @Cacheable(cacheNames = "cache:shiro:role", key = "'id-' + #id")
    public Set<Role> findRoleByUserId(Long id) {
        Set<Role> setRole = roleMapper.selectRoleByUserId(id);

        for (Role role : setRole) {
            Set<Permission> setPermission = permissionMapper.selectPermissionByRoleId(role.getId());
            role.setPermissions(setPermission);
        }

        return setRole;
    }

}
