package com.ljryh.client.mapper;

import com.ljryh.client.entity.SDatabase;
import com.ljryh.client.entity.SDatabaseContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据库生成具体内容 Mapper 接口
 * </p>
 *
 * @author ljryh
 * @since 2023-02-27
 */
public interface SDatabaseContentMapper extends BaseMapper<SDatabaseContent> {

    Integer createTable(@Param("database") SDatabase database,@Param("list") List<SDatabaseContent> databaseContentList);

    List<Map<String, Object>> getTableData(Map<String, Object> map);

    Integer getTableDataCount(Map<String, Object> map);
}
