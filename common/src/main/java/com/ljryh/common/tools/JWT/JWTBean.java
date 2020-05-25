package com.ljryh.common.tools.JWT;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Data
public class JWTBean implements Serializable {

	private static final long serialVersionUID = 57112020841617720L;

	private String token;
	private String userName;
	private String password;
	private String publicKey;
	private String privateKey;
	private String ip;
	private Date createTime;
	private Integer effectiveTime;
	private Integer useCount;

	private Integer sex;
	private Long creator;
	private LocalDateTime updateTime;
	private Long updator;
	private LocalDateTime todayTime;
	private Integer todayCount;
}