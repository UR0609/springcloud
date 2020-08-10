package com.ljryh.client.service.shiro.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljryh.client.config.auth.TokenGenerator;
import com.ljryh.client.entity.shiro.Permission;
import com.ljryh.client.entity.shiro.Role;
import com.ljryh.client.entity.shiro.SysToken;
import com.ljryh.client.entity.shiro.User;
import com.ljryh.client.mapper.shiro.PermissionMapper;
import com.ljryh.client.mapper.shiro.RoleMapper;
import com.ljryh.client.mapper.shiro.SysTokenMapper;
import com.ljryh.client.mapper.shiro.UserMapper;
import com.ljryh.client.service.shiro.ShiroService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
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
    private SysTokenMapper sysTokenRepository;


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
    public Map<String, Object> createToken(Long userId) {
        Map<String, Object> result = new ConcurrentHashMap<>();
        //生成一个token
        String token = TokenGenerator.generateValue();
        //当前时间
        LocalDateTime now = LocalDateTime.now();
        //过期时间
        LocalDateTime expireTime = now.plusHours(EXPIRE);
        //判断是否生成过token
        SysToken tokenEntity = sysTokenRepository.findByUserId(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysToken();
            tokenEntity.setUserId(userId);
            //保存token
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            sysTokenRepository.insert(tokenEntity);
        } else {
            QueryWrapper<SysToken> queryWrapper = new QueryWrapper<>();

            queryWrapper.eq("user_id", tokenEntity.getUserId());

            //更新token
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            sysTokenRepository.update(tokenEntity,queryWrapper);
        }
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
        SysToken byToken = findByToken(token);
        //生成一个token
        token = TokenGenerator.generateValue();
        //修改token
        byToken.setToken(token);
        //使前端获取到的token失效
        sysTokenRepository.insert(byToken);
    }

    @Override
    public SysToken findByToken(String accessToken) {
        return sysTokenRepository.findByToken(accessToken);

    }

    @Override
    public User findByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public Set<Role> findRoleByUserId(Long id) {
        Set<Role> setRole = roleMapper.selectRoleByUserId(id);

        for(Role role : setRole){
            Set<Permission> setPermission = permissionMapper.selectPermissionByRoleId(role.getRoleId());
            role.setPermissions(setPermission);
        }

        return setRole;
    }

}
