package com.ljryh.client.mapper;

import com.ljryh.client.entity.SMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljryh
 * @since 2021-01-06
 */
public interface SMenuMapper extends BaseMapper<SMenu> {

    List<SMenu> getPermissionNameByMenuId(SMenu entity);

    void deleteSMenuByParentId(SMenu menu);

    List<SMenu> selAllMenu();
}
