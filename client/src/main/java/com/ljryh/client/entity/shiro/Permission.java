package com.ljryh.client.entity.shiro;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
public class Permission implements Serializable {
    private static final long serialVersionUID = 2001379316230695883L;
    @Id
    private Integer permissionId;
    private String permissionName;
    private String permission;
}
