package com.ljryh.client.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageResponse implements Serializable {

    private static final long serialVersionUID = -3816770964793753188L;

    @TableField(exist = false)
    private Integer pageNo;
    @TableField(exist = false)
    private Integer pageSize;

}
