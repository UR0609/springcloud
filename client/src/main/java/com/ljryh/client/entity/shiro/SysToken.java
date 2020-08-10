package com.ljryh.client.entity.shiro;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SysToken implements Serializable {

    private static final long serialVersionUID = -2669694438891181979L;
    /**
     * 用户ID
     */
    @Id
    private Long userId;

    /**
     * token
     */
    private String token;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
