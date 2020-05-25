package com.ljryh.common.tools.JWT;

import java.util.UUID;

public class Constant {
    public static final String JWT_ID = UUID.randomUUID().toString();

    /**
     * 加密密文
     */
//    public static final String JWT_SECRET = "bdZqG3y7jyv8mfpT";
    public static final int JWT_TTL = 60 * 1000 * 10;  //millisecond
}
