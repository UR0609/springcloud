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
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "oper_id", type = IdType.AUTO)
    private Long operId;

    /**
     * 功能模块
     */
    private String operModul;

    /**
     * 操作类型
     */
    private String operType;

    /**
     * 操作描述
     */
    private String operDesc;

    /**
     * 请求参数
     */
    private String operRequParam;

    /**
     * 返回参数
     */
    private String operRespParam;

    /**
     * 操作员ID
     */
    private Long operUserId;

    /**
     * 操作员名称
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
