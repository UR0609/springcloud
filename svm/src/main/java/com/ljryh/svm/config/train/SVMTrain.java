package com.ljryh.svm.config.train;

import com.ljryh.svm.config.construct.Constant;
import com.ljryh.svm.entity.Configuration;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_parameter;
import libsvm.svm_problem;

/**
 * @author ljryh
 * @date 2021/11/23 16:47
 */
public class SVMTrain {

    public static svm_model ModelTraining(svm_problem sp, Configuration configuration) {
        // 主要优化 c与gamma
        svm_parameter prm = new svm_parameter();
        prm.svm_type = svm_parameter.C_SVC;
        prm.kernel_type = svm_parameter.RBF;
        prm.C = configuration.getC() == null ? Constant.PRM_C : configuration.getC();
        prm.eps = Constant.PRM_EPS;
        prm.gamma = configuration.getGamma() == null ? Constant.PRM_GAMMA : configuration.getGamma();
        prm.probability = Constant.PRM_PROBABILITY;
        prm.cache_size = Constant.PRM_CACHE_SIZE;

        svm_model model = svm.svm_train(sp, prm);
        return model;
    }

}
