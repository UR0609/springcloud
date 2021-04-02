package com.ljryh.client.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 模型字段
 * </p>
 *
 * @author ljryh
 * @since 2021-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SModelField implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long modelId;

    private String name;

    private String code;

    /**
     * 数据库字段类型：
1:字符串 varchar
2:短整型 smallint
3:整型     int
4:长整型 bigint
5:单精度 float
6:双精度 double
7:日期 datetime
8:日期时间 datatime
     */
    private Integer type;

    /**
     * 字段长度
     */
    private Integer length;

    /**
     * 是否为主键 0:否,1:是
     */
    private Integer isKey;

    /**
     * 是否为空 0:否,1:是
     */
    private Integer isEmpty;

    /**
     * 是否为搜索条件 0:否,1:是
     */
    private Integer isCheck;

    /**
     * 排序
     */
    private Integer sort;

    private String remarks;

    private LocalDateTime createTime;

    private Long creater;

    private LocalDateTime updateTime;

    private Long updator;

    private Integer del;


}
