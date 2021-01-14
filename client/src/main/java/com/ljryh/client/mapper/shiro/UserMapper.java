package com.ljryh.client.mapper.shiro;

import com.ljryh.client.entity.shiro.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljryh
 * @since 2020-02-13
 */
public interface UserMapper extends BaseMapper<User> {

    User findByUsername(String username);

    User findByUserId(Long id);

    Long getRoleIdByUserId(User user);
}
