package com.ljryh.client.mapper.shiro;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljryh.client.entity.shiro.SysToken;

public interface SysTokenMapper extends BaseMapper<SysToken> {
    /**
     * 通过token查找
     * @param token
     * @return
     */
    SysToken findByToken(String token);

    /**
     * 通过userID查找
     * @param userId
     * @return
     */
    SysToken findByUserId(Long userId);
}
