package com.ljryh.svm.controller;

import com.ljryh.common.entity.CallResult;
import com.ljryh.svm.config.construct.Constant;
import com.ljryh.svm.config.construct.LibSVM;
import com.ljryh.svm.entity.RequestEntity;
import com.ljryh.svm.utils.RedisUtils;
import io.swagger.annotations.Api;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ljryh
 * @date 2021/10/11 16:26
 */
@Api(tags = "SVM")
@RestController
public class IndexController {

    @Resource
    private RedisUtils redisUtils;

    @PostMapping(value = "/svm/test.do",produces = "application/json;charset=utf-8")
    public CallResult svmTest(@RequestBody RequestEntity entity){
        svm_model model = LibSVM.svm_maps.get(entity.getTypeName());
        List<svm_node> svmNodeList = redisUtils.exampleToTFIDF(entity.getData(),entity.getTypeName());
        svm_node[] svmNodesTest = svmNodeList.toArray(new svm_node[svmNodeList.size()]);
        double result = svm.svm_predict(model, svmNodesTest);
        System.out.println("结果:"+result);
        double d = Double.parseDouble(redisUtils.get(entity.getTypeName() + Constant.SVM_INTENTION_COUNT).toString());
        double[] l = new double[(int) d];
        double result_prob = svm.svm_predict_probability(model, svmNodesTest, l);        //带预测概率的分类测试
        System.out.println("Result with prob " + result_prob);
        for (int i = 1; i <= 24; i++) {
            System.out.println("Result  " + i + ":" + l[i - 1]);
        }
        return CallResult.success("结果:"+result);
    }
}
