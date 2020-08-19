package com.ljryh.client.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ljryh
 * @since 2020-08-12
 */
@Data
@TableName("s_permission")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Permission extends PageResponse implements Serializable {

    private static final long serialVersionUID = -4816453797275500733L;

    private Integer id;

    private String permission;

    private String permissionName;


}
