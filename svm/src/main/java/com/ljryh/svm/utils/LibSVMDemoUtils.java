package com.ljryh.svm.utils;

import com.ljryh.common.utils.JacksonUtils;
import libsvm.*;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author ljryh
 * @date 2021/11/18 10:37
 */
public class LibSVMDemoUtils {

    public static void main(String[] args) {

        /** 1、构造样本 **/
        final int sampleNum = 100;//样本数量
        double[][] features = new double[sampleNum][];//特征向量数组 本例中即xy坐标构成的向量
        double[] targetValues = new double[sampleNum];//分类值数组 本例中即点属于哪个象限
        Random random = new Random(233);
        for (int i = 0; i < sampleNum; i++) {
            //每次循环随机生成一条样本数据
            //随机在[-100,100)直接生成x、y坐标
            double x = random.nextInt(200) - 100;
            double y = random.nextInt(200) - 100;
            int quadrant = getQuadrant(x, y);//样本分类，实际应用中分类一般需要手工录入，本例中获取象限有公式我们就自己生成了
            //坐标值归一化，特征向量的值需要在[0,1]间，所以需要归一化
            double normalizationX = (x + 100) / 200;
            double normalizationY = (y + 100) / 200;
            //把特征向量和分类值丢到数组里
            features[i] = new double[]{normalizationX, normalizationY};
            targetValues[i] = quadrant;
        }

        /** 2、训练模型 **/
        svm_model model = train(features, targetValues);

        /** 3、识别分类 **/
        //待识别向量
        svm_node[] test = new svm_node[]{new svm_node(), new svm_node()};
        test[0].index = 1;
        test[0].value = -45.5;
        test[1].index = 2;
        test[1].value = 20.2;
        //归一化
        test[0].value = (test[0].value+100)/200;
        test[1].value = (test[1].value+100)/200;

        double result = svm.svm_predict(model, test);    // 不带概率的分类测试
        System.out.println("所在象限 " + result);//所在象限 3.0

        double[] l = new double[4];
        double result_prob = svm.svm_predict_probability(model, test, l);        //带预测概率的分类测试
        System.out.println("Result with prob " + result_prob);
        System.out.println("Probability " + l[0] + "\t" + l[1]);
    }

    public static svm_node[][] listToSvmNode(List<List<svm_node>> list){

        svm_node[][] svmNodes = new svm_node[list.size()][];
        int i = 0;
        for (List<svm_node> svm_nodeList:list){
            svmNodes[i] =  svm_nodeList.toArray(new svm_node[svm_nodeList.size()]);
            i++;
        }

        return svmNodes;
    }

    /**
     * 训练模型
     *
     * @param features
     * @param targetValues
     * @return
     */
    @SneakyThrows
    public static svm_model train(double[][] features, double[] targetValues) {
        List<List<svm_node>> testList = new ArrayList<>();
        for (int i = 0; i < features.length; i++) {
            double[] feature = features[i];
            List<svm_node> svmNodeList = new ArrayList<>();
            for (int i1 = 0; i1 < feature.length; i1++) {
                svm_node svmNode = new svm_node();
                svmNode.index = i1 + 1;//index从1开始，所以要+1
                svmNode.value = feature[i1];
                svmNodeList.add(svmNode);
            }
            testList.add(svmNodeList);
        }
        svm_node[][] svmNodes = listToSvmNode(testList);

        System.out.println(JacksonUtils.objToJson(svmNodes));

        svm_problem sp = new svm_problem();
        sp.x = svmNodes;
        sp.y = targetValues;
        sp.l = features.length;

        //调参什么的，太深奥了，先用默认值好了
        svm_parameter prm = new svm_parameter();
        prm.svm_type = svm_parameter.C_SVC;
        prm.kernel_type = svm_parameter.RBF;
        prm.C = 1000;
        prm.eps = 0.0000001;
        prm.gamma = 10;
        prm.probability = 1;
        prm.cache_size = 1024;

        svm_model model = svm.svm_train(sp, prm);           //训练分类
        return model;
    }

    /**
     * 判断输入的点在第几象限
     *
     * @param x
     * @param y
     * @return 1 2 3 4
     * 2  |  1
     *----|----
     * 3  |  4
     */
    public static int getQuadrant(double x, double y) {
        if (x > 0) {
            if (y > 0) {
                return 1;
            } else {
                return 4;
            }
        } else {
            if (y > 0) {
                return 2;
            } else {
                return 3;
            }
        }
    }

}
