package com.ljryh.client.entity.shiro;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录传输类
 */
@Data
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = 4843588092734237018L;
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
}
