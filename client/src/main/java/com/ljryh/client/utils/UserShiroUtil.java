package com.ljryh.client.utils;

import com.ljryh.client.entity.shiro.User;
import org.apache.shiro.SecurityUtils;

public class UserShiroUtil {

    public Long userId;
    public String userName;

    public static Long getUserId() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user.getId();
    }

    public static String getUserName() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user.getName();
    }
}
