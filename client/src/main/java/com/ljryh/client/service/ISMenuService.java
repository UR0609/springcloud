package com.ljryh.client.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljryh.client.entity.SMenu;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ljryh
 * @since 2021-01-06
 */
public interface ISMenuService extends IService<SMenu> {

    List<SMenu> selectTree(QueryWrapper<SMenu> wrapper);
}
