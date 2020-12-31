package com.ljryh.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljryh.client.entity.Permission;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljryh
 * @since 2020-08-12
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    Set<Permission> selectPermissionByRoleId(Long roleId);

}
