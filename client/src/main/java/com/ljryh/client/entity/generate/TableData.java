package com.ljryh.client.entity.generate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ljryh.client.entity.PageResponse;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ljryh
 * @date 2021/10/16 16:36
 */
@Data
public class TableData extends PageResponse implements Serializable {

    private static final long serialVersionUID = 392555930831393923L;

    private String tableName;   // 表名称
    private String tableRows;   // 表里所存多少行数据
    private String indexLength; // 索引长度
    private String autoIncrement;   // 做自增主键的自动增量当前值
    private String tableComment;    // 表的注释、备注
    private String tableCollation;  // 表的字符校验编码集
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;   // 表的创建时间

}
