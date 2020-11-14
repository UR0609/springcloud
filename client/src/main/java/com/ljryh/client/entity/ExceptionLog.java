package com.ljryh.client.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author ljryh
 * @since 2020-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExceptionLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "exc_id", type = IdType.AUTO)
    private Long excId;

    /**
     * 请求参数
     */
    private String excRequParam;

    /**
     * 异常名
     */
    private String excName;

    /**
     * 异常信息
     */
    private String excMessage;

    /**
     * 操作员ID
     */
    private Long operUserId;

    /**
     * 操作员姓名
     */
    private String operUserName;

    /**
     * 操作方法
     */
    private String operMethod;

    /**
     * 请求URI
     */
    private String operUri;

    /**
     * 请求IP
     */
    private String operIp;

    /**
     * 版本号
     */
    private String operVer;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;


}
