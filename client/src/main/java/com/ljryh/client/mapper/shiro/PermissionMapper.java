package com.ljryh.client.mapper.shiro;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljryh.client.entity.shiro.Permission;

import java.util.Set;

public interface PermissionMapper extends BaseMapper<Permission> {
    Set<Permission> selectPermissionByRoleId(Integer roleId);
}
