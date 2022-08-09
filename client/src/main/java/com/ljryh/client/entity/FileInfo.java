package com.ljryh.client.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ljryh
 * @version 1.0
 * @date 2022/8/8 15:41
 */
@Data
@TableName("s_file_info")
public class FileInfo implements Serializable {
    private static final long serialVersionUID = -1802371820642777289L;
    private String id;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 文件大小
     */
    private long fileSize;
    /**
     * 状态
     */
    private String fileStatus;
    /**
     * 分类
     */
    private String classified;
    /**
     * md5校验值
     */
    private String md5;
    /**
     * 访问全路径
     */
    private String fileTotalPath;
    private String remarks;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private Long creater;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private Long updator;

    /**
     * 删除标识
     */
    private Integer del;
}
