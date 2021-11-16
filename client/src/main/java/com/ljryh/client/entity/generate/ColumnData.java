package com.ljryh.client.entity.generate;

import com.ljryh.client.entity.PageResponse;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ljryh
 * @date 2021/10/16 17:01
 */
@Data
public class ColumnData extends PageResponse implements Serializable {
    private static final long serialVersionUID = -5457231533759687251L;

    private String columnName;  // 名字
    private String columnType;  // 类型
    private String columnComment;   // 备注

    private Boolean dataShow;   // 是否显示
    private Boolean dataQuery;  // 是否查询

}
