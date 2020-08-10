package com.ljryh.client.entity.shiro;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.Set;

@Data
public class Role implements Serializable {

    private static final long serialVersionUID = -5533101028903913056L;
    @Id
    private Integer roleId;
    private String roleName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission",
            joinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "roleId")},
            inverseJoinColumns = {@JoinColumn(name = "PERMISSION_ID", referencedColumnName = "permissionId")})
    private Set<Permission> permissions;

}
