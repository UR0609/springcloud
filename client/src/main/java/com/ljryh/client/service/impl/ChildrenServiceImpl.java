package com.ljryh.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljryh.client.entity.Children;
import com.ljryh.client.mapper.ChildrenMapper;
import com.ljryh.client.service.IChildrenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljryh
 * @since 2020-02-13
 */
@Slf4j
@Service
@Transactional(rollbackFor=Exception.class)
public class ChildrenServiceImpl extends ServiceImpl<ChildrenMapper, Children> implements IChildrenService {

    @Override
    @Cacheable(value = "cache:childrenList", key = "'children-' + #page.current+'_'+#page.size")
    public IPage<Children> page(@Param("page") IPage<Children> page, @Param("ew") QueryWrapper<Children> wrapper) {
        log.info("<--------------------------查询数据List  : 第{}页，{}条数据------------------------------>",page.getCurrent(),page.getSize());
        return this.baseMapper.selectPage(page, wrapper);
    }

}
