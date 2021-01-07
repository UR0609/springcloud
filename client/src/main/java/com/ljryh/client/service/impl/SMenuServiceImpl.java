package com.ljryh.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljryh.client.entity.SMenu;
import com.ljryh.client.mapper.SMenuMapper;
import com.ljryh.client.service.ISMenuService;
import com.ljryh.client.utils.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ljryh
 * @since 2021-01-06
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SMenuServiceImpl extends ServiceImpl<SMenuMapper, SMenu> implements ISMenuService {

    @Override
    public List<SMenu> list(Wrapper<SMenu> wrapper) {

        List<SMenu> list = this.baseMapper.selectList(wrapper);

        // 封装树形
        TreeUtils menuTree = new TreeUtils(list);
        list = menuTree.builTree();

        return list;
    }

    @Override
    public List<SMenu> selectTree(QueryWrapper<SMenu> wrapper) {
        List<SMenu> list = this.baseMapper.selectList(wrapper);
        return list;
    }
}
