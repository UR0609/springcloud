package com.ljryh.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljryh.client.entity.SDatabase;
import com.ljryh.client.entity.SDatabaseContent;
import com.ljryh.client.mapper.SDatabaseContentMapper;
import com.ljryh.client.mapper.SDatabaseMapper;
import com.ljryh.client.service.ISDatabaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 数据库生成 服务实现类
 * </p>
 *
 * @author ljryh
 * @since 2023-02-09
 */
@Service
public class SDatabaseServiceImpl extends ServiceImpl<SDatabaseMapper, SDatabase> implements ISDatabaseService {

    @Resource
    private SDatabaseContentMapper sDatabaseContentMapper;

    @Override
    public int createTable(SDatabase entity) {
        SDatabase database = this.baseMapper.selectById(entity.getId());
        QueryWrapper<SDatabaseContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("database_id",entity.getId());
        // 根据entity.getId() 获取 SDatabaseContent
        List<SDatabaseContent> databaseContentList = sDatabaseContentMapper.selectList(queryWrapper);
        Integer result = sDatabaseContentMapper.createTable(database,databaseContentList);
        return result;
    }
}
