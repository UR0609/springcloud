package com.ljryh.client.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljryh.client.entity.Children;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljryh
 * @since 2020-02-13
 */
public interface IChildrenService extends IService<Children> {

    IPage<Children> page(@Param("page") IPage<Children> page, @Param("ew") QueryWrapper<Children> wrapper);
}
