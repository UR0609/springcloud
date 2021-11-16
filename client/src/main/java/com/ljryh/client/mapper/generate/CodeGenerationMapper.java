package com.ljryh.client.mapper.generate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljryh.client.entity.TablesEntity;
import com.ljryh.client.entity.generate.ColumnData;
import com.ljryh.client.entity.generate.TableData;

import java.util.List;

/**
 * @author ljryh
 * @date 2021/5/24 16:25
 */
public interface CodeGenerationMapper extends BaseMapper<TablesEntity> {

    List<TableData> getTablesData(TableData entity);

    List<ColumnData> getColumnData(TableData entity);
}
