package com.ljryh.svm.service.impl;

import com.ljryh.svm.config.construct.Constant;
import com.ljryh.svm.entity.Configuration;
import com.ljryh.svm.service.SVMStrategy;
import com.ljryh.svm.utils.RedisUtils;
import libsvm.svm_model;

/**
 * @author ljryh
 * @date 2021/11/23 16:13
 */
public class TxtStrategy implements SVMStrategy {

    @Override
    public svm_model getModle(Configuration configuration, RedisUtils redisUtils) {
        svm_model svmModel = new svm_model();
        return svmModel;
    }

    @Override
    public int getType() {
        return Constant.SVM_TXT;
    }
}
