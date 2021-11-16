package com.ljryh.client.service.generate;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljryh.client.entity.TablesEntity;
import com.ljryh.client.entity.generate.ColumnData;
import com.ljryh.client.entity.generate.TableData;

import java.util.List;

/**
 * @author ljryh
 * @date 2021/5/24 16:19
 */
public interface ICodeGenerationService extends IService<TablesEntity> {

    List<TableData> getTablesData(TableData entity);

    List<ColumnData> getColumnData(TableData entity);

    void generateCode(List<ColumnData> list, String tableName);
}
