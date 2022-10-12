package com.ljryh.client.entity.shiro;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ljryh.client.entity.PageResponse;
import com.ljryh.client.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
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
    @NotBlank(groups = {add.class})
    private String name;

    /**
     * 年龄
     */
    @NotNull(groups = {add.class},message = "年龄不能为空")
    private Integer age;

    /**
     * 邮箱
     */
    private String email;

    private String phone;

    private String remarks;

    private Long creater;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    private Long updator;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    private Integer del;

    /**
     * 新增时候参数校验
     */
    public interface list{}

    /**
     * 新增时候参数校验
     */
    public interface add{}

    /**
     * 修改时参数校验
     */
    public interface mod{}
    /**
     * 删除时参数校验
     */
    public interface del{}


}
