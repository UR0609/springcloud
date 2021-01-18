package com.ljryh.client.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljryh.client.entity.Role;
import com.ljryh.client.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljryh
 * @since 2020-08-12
 */
public interface IRoleService extends IService<Role> {

    IPage<Role> page(@Param("page") IPage<Role> page, @Param("ew") QueryWrapper<Role> wrapper);

    List<RoleMenu> getMenuIdByRoleId(Role entity);

    boolean bind(Role entity);
}
