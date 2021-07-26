package com.ljryh.client.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ljryh
 * @date 2021/5/24 13:25
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class TablesEntity extends PageResponse implements Serializable {

    private static final long serialVersionUID = -6069970066198202940L;

    private String tableCatalog;    // 表目录
    private String tableSchema;     // 表架构图
    private String tableName;       // 表名字
    private String tableType;       // 表类型
    private String engine;          // 引擎
    private Long version;           // 版本
    private String rowFormat;       // 行格式
    private Long tableRows;         // 行数
    private Long avgRowLength;      // 平均行长
    private Long dataLength;        // 数据长度
    private Long maxDataLength;     // 最大数据长度
    private Long indexLength;       // 索引长度
    private Long dataFree;          // 数据碎片
    private Long autoIncrement;     // 自动递增
    private Date createTime;        // 创建时间
    private Date UPDATE_TIME;       // 修改时间
    private Date CHECK_TIME;        //
    private String tableCollation;  // 表类型校对
    private Long checksum;
    private String createOptions;   // 创建选项
    private String tableComment;    // 表说明

}
