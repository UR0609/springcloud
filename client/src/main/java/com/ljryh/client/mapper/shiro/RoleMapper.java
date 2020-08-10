package com.ljryh.client.mapper.shiro;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljryh.client.entity.shiro.Role;

import java.util.Set;

public interface RoleMapper extends BaseMapper<Role> {
    Set<Role> selectRoleByUserId(Long userId);
}
