package com.ljryh.client.service.impl;

import com.ljryh.client.entity.User;
import com.ljryh.client.mapper.UserMapper;
import com.ljryh.client.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljryh
 * @since 2020-02-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
