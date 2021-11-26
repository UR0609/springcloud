package com.ljryh.svm.service;

import com.ljryh.svm.entity.Configuration;
import com.ljryh.svm.utils.RedisUtils;
import libsvm.svm_model;

/**
 * @author ljryh
 * @date 2021/11/23 16:11
 */
public interface SVMStrategy {

    // 获取训练模型
    svm_model getModle(Configuration configuration, RedisUtils redisUtils);

    // 返回 type
    int getType();
}
