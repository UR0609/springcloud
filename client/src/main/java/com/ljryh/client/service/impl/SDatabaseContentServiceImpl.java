package com.ljryh.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljryh.client.entity.SDatabase;
import com.ljryh.client.entity.SDatabaseContent;
import com.ljryh.client.mapper.SDatabaseContentMapper;
import com.ljryh.client.mapper.SDatabaseMapper;
import com.ljryh.client.service.ISDatabaseContentService;
import com.ljryh.common.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据库生成具体内容 服务实现类
 * </p>
 *
 * @author ljryh
 * @since 2023-02-27
 */
@Service
public class SDatabaseContentServiceImpl extends ServiceImpl<SDatabaseContentMapper, SDatabaseContent> implements ISDatabaseContentService {

    @Resource
    private SDatabaseMapper databaseMapper;

    @Override
    public List<SDatabaseContent> list(Wrapper<SDatabaseContent> queryWrapper) {
        return super.list(queryWrapper);
    }


    @Override
    public Map<String,Object> getTableData(Map<String, Object> map) throws ParseException {
        // pageNo 判断是否为空
        if (map.get("pageNo") == null) {
            map.put("pageNo", 0);
        } else {
            map.put("pageNo", (Integer.parseInt(map.get("pageNo").toString()) - 1) * Integer.parseInt(map.get("pageSize").toString()));
        }
        // pageSize 判断是否为空
        map.putIfAbsent("pageSize", 10);
        // 根据数据库id查询数据库表
        SDatabaseContent databaseContent = new SDatabaseContent();
        Integer databaseId = (Integer) map.get("databaseId");
        databaseContent.setDatabaseId(databaseId.longValue());

        QueryWrapper<SDatabaseContent> wrapper = new QueryWrapper<>();
        wrapper.setEntity(databaseContent);
        List<SDatabaseContent> list = this.list(wrapper);

        extracted(map, list);

        // 根据databaseId 获取 SDatabase
        SDatabase database = databaseMapper.selectById(databaseId.longValue());

        map.put("database", database);
        map.put("list", list);
        Map<String,Object> resultMap = new HashMap<>();
        Integer total = this.baseMapper.getTableDataCount(map);
        resultMap.put("total", total);
        if (total > 0) {
            List<Map<String, Object>> resultList = this.baseMapper.getTableData(map);
            resultMap.put("records", resultList);
        }
        return resultMap;
    }

    private static void extracted(Map<String, Object> map, List<SDatabaseContent> list) throws ParseException {
        // 循环查询条件
        for (SDatabaseContent entity : list) {
            // entity.getQueryType() 不为空
            if (entity.getQueryType() != null) {
                // 如果是时间类型
                if (entity.getQueryType() == 10) {
                    // 如果开始时间不为空
                    if (map.get(entity.getName() + "Start") != null) {
                        entity.setQueryConditionStart(DateUtils.stringToLocalDateTime(map.get(entity.getName() + "Start").toString()));
                    }
                    // 如果结束时间不为空
                    if (map.get(entity.getName() + "End") != null) {
                        entity.setQueryConditionEnd(DateUtils.stringToLocalDateTime(map.get(entity.getName() + "End").toString()));
                    }
                } else {
                    // 如果不是时间类型 且不为空
                    if (map.get(entity.getName()) != null) {
                        entity.setQueryCondition(map.get(entity.getName()).toString());
                    }
                }
            }
        }
    }


}
