package com.ljryh.client.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 数据库生成具体内容
 * </p>
 *
 * @author ljryh
 * @since 2023-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SDatabaseContent extends PageResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 数据库生成表Id
     */
    private Long databaseId;

    /**
     * 字段名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 字段类型
     */
    private Long fieldType;

    /**
     * 字段名称
     */
    @TableField(exist = false)
    private String fieldName;

    /**
     * 字段value
     */
    @TableField(exist = false)
    private String fieldValue;

    /**
     * 字段长度
     */
    private String fieldLength;

    /**
     * 查询类型
     */
    private Long queryType;

    /**
     * 查询名称
     */
    @TableField(exist = false)
    private String queryName;

    /**
     * 是否验空（0：字段可为空，1：不可为空）
     */
    private Integer isNotNull;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 输入类型
     */
    private Long inputType;

    /**
     * 输入名称
     */
    @TableField(exist = false)
    private String inputName;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 输入长度最小验证
     */
    private Integer verifyMin;

    /**
     * 输入长度最大验证
     */
    private Integer verifyMax;

    /**
     * 输入类型验证
     */
    private Integer verifyType;

    /**
     * 输入类型验证的名称
     */
    @TableField(exist = false)
    private String verifyName;

    /**
     * 其他验证（例 邮箱 电话等）
     */
    private Long verifyOther;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private Long creater;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private Long updater;

    /**
     * 删除标识
     */
    private Integer del;


}
