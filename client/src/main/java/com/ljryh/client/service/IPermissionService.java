package com.ljryh.client.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljryh.client.entity.Permission;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljryh
 * @since 2020-08-12
 */
public interface IPermissionService extends IService<Permission> {

    IPage<Permission> page(@Param("page") IPage<Permission> page, @Param("ew") QueryWrapper<Permission> wrapper);

}
