package com.ljryh.client.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserRole implements Serializable {
    private static final long serialVersionUID = -8753576057293622758L;

    private Long userId;
    private Long roleId;
}
