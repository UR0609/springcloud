package com.ljryh.client.service.shiro;

import com.ljryh.client.entity.Role;
import com.ljryh.client.entity.shiro.SysToken;
import com.ljryh.client.entity.shiro.User;

import java.util.Map;
import java.util.Set;

public interface ShiroService {
    /**
     * Find user by username
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * create token by userId
     * @param user
     * @return
     */
    Map<String,Object> createToken(User user);

    /**
     * logout
     * @param token
     */
    void logout(String token);

    /**
     * find token by token
     * @param accessToken
     * @return
     */
    SysToken findByToken(String accessToken);

    /**
     * find user by userId
     * @param userId
     * @return
     */
    User findByUserId(Long userId);

    Set<Role> findRoleByUserId(Long id);
}
