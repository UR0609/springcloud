package com.ljryh.client.entity.shiro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录传输类
 */
@Data
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = 4843588092734237018L;
    @ApiModelProperty(value = "用户名", name = "username", required = true, example = "admin")
    @NotBlank(message = "用户名不能为空")
    private String username;
    @ApiModelProperty(value = "密码", name = "password", required = true, example = "123")
    @NotBlank(message = "密码不能为空")
    private String password;
}
