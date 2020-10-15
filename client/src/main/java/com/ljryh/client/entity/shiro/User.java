package com.ljryh.client.entity.shiro;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ljryh.client.entity.PageResponse;
import com.ljryh.client.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author ljryh
 * @since 2020-02-13
 */
@Data
@TableName("s_user")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User extends PageResponse implements Serializable {


    private static final long serialVersionUID = 2683417209070757641L;

    private Long id;

    private String username;
    private String password;

    @TableField(exist = false)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "roleId")})
    private Set<Role> roles;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;


}
