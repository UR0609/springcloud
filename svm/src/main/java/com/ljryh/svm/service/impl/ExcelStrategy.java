package com.ljryh.svm.service.impl;

import com.ljryh.svm.config.construct.Constant;
import com.ljryh.svm.config.train.SVMTrain;
import com.ljryh.svm.entity.Configuration;
import com.ljryh.svm.entity.ExcelData;
import com.ljryh.svm.service.SVMStrategy;
import com.ljryh.svm.utils.RedisUtils;
import com.ljryh.svm.utils.SVMExcelUtils;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_problem;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ljryh
 * @date 2021/11/23 16:12
 */
@Slf4j
public class ExcelStrategy implements SVMStrategy {

    @Override
    public svm_model getModle(Configuration configuration, RedisUtils redisUtils) {
        log.info("开始 清除{} redis 结束",configuration.getName());
        redisUtils.likeDel(configuration.getName());
        log.info("结束 清除{} redis 结束",configuration.getName());
        log.info("开始 读取Excel 文件 路径{}",configuration.getPath());
        List<ExcelData> list = SVMExcelUtils.readExcelData(configuration, redisUtils);
        log.info("结束 读取Excel 文件");
        log.info("开始制作训练样本");
        svm_problem sp = generateTrainingSamples(list, redisUtils, configuration.getName());
        log.info("结束制作训练样本");
        return SVMTrain.ModelTraining(sp, configuration);
    }

    @Override
    public int getType() {
        return Constant.SVM_EXCEL;
    }

    // 生成训练样本
    private svm_problem generateTrainingSamples(List<ExcelData> list, RedisUtils redisUtils, String typeName) {
        List<List<svm_node>> testList = new ArrayList<>();
        svm_problem sp = new svm_problem();
        double[] targetValues = new double[list.size()];
        int count = 0;

        for (ExcelData excelData : list) {
            // 将示例转化为TF-IDF
            List<svm_node> svmNodeList = redisUtils.exampleToTFIDF(excelData.getExample(),typeName);
            testList.add(svmNodeList);
            // 获取意图对应序号
            targetValues[count] = redisUtils.getTargetValues(typeName,excelData.getIntention());
            count++;
        }

        svm_node[][] svmNodes = listToSvmNode(testList);
        sp.x = svmNodes;
        sp.y = targetValues;
        sp.l = count - 1;
        return sp;
    }

    // 格式转换为样本训练格式
    private svm_node[][] listToSvmNode(List<List<svm_node>> list) {
        svm_node[][] svmNodes = new svm_node[list.size()][];
        int i = 0;
        for (List<svm_node> svm_nodeList : list) {
            svmNodes[i] = svm_nodeList.toArray(new svm_node[svm_nodeList.size()]);
            i++;
        }
        return svmNodes;
    }
}
