package com.ljryh.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljryh.client.entity.Role;
import com.ljryh.client.entity.RoleMenu;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    List<RoleMenu> getMenuIdByRoleId(Role entity);

    void deleteByRoleId(Role entity);

}
