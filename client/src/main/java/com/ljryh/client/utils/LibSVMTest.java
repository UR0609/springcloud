package com.ljryh.client.utils;

import libsvm.*;
import lombok.SneakyThrows;

import java.util.List;

/**
 * @author ljryh
 * @date 2021/9/29 13:53
 */
public class LibSVMTest {


    public static int[] twoSum(int[] nums, int target) {

        int start;
        int end;
        int l = nums.length - 1;
        System.out.println(nums.length);

        for (int i = 0; i <= l; i++) {
            for (int j = i + 1; j <= l; j++) {
                System.out.println("i:" + i + ",j:" + j);
                if (nums[i] + nums[j] == target) {
                    int[] result = {i, j};
                    return result;
                }
            }

        }

        return null;

    }

    private static svm_node[][] listToSvmNode(List<List<svm_node>> list){

        svm_node[][] svmNodes = new svm_node[list.size()][];
        int i = 0;
        for (List<svm_node> svm_nodeList:list){
            svmNodes[i] =  svm_nodeList.toArray(new svm_node[svm_nodeList.size()]);
            i++;
        }

        return svmNodes;
    }

    @SneakyThrows
    public static void main(String[] args) {



//        List<List<svm_node>> testList = new ArrayList<>();
//
//        List<svm_node> svmNodeList1 = new ArrayList<>();
//        for (int i = 0 ; i < 10 ; i++){
//            svm_node svmNode = new svm_node();
//            svmNode.index = i;
//            svmNode.value = i;
//            svmNodeList1.add(svmNode);
//        }
//
//        testList.add(svmNodeList1);
//
//        List<svm_node> svmNodeList2 = new ArrayList<>();
//        for (int i = 10 ; i < 20 ; i++){
//            svm_node svmNode = new svm_node();
//            svmNode.index = i;
//            svmNode.value = i;
//            svmNodeList2.add(svmNode);
//        }
//
//        testList.add(svmNodeList2);
//
//        svm_node[][] svmNodes = listToSvmNode(testList);
//
//        System.out.println(JacksonUtil.objToJson(testList));
//
//        String StandardQuestion = "你这个保险只赔疾病吗?" +
//                "只有生病给赔吗" +
//                "意外伤亡也一样给保吗" +
//                "意外保吗" +
//                "保障范围是什么" +
//                "你们这个是针对疾病吗" +
//                "你们这个是针对肿瘤吗" +
//                "你们这个是意外险" +
//                "你们这个是重疾险" +
//                "除了疾病之外，还保什么吗"
//                + "我生病可以多次赔付吗" +
//                "那可以赔几次呢" +
//                "我几次病可以一直陪吗" +
//                "能一直赔吗" +
//                "最多能够给个几次" +
//                "最多能够给个三次" +
//                "最多能够给个两次" +
//                "最多能够给个五次" +
//                "最多能赔几次" +
//                "这个保险报了，那就不能重复了吗" +
//                "你这种分三次是什么意思？" +
//                "他为什么赔付三次呀" +
//                "你们假如一次性理赔之后，我再还能续保吗";
//        // 只保疾病吗
//        List<String> list1 = new ArrayList<>();
//        list1.add("你这个保险只赔疾病吗");
//        list1.add("只有生病给赔吗");
//        list1.add("意外伤亡也一样给保吗");
//        list1.add("意外保吗");
//        list1.add("保障范围是什么");
//        list1.add("你们这个是针对疾病吗");
//        list1.add("你们这个是针对肿瘤吗");
//        list1.add("你们这个是意外险");
//        list1.add("你们这个是重疾险");
//        list1.add("除了疾病之外，还保什么吗");
//        // 最多可以赔几次
//        List<String> list2 = new ArrayList<>();
//        list2.add("我生病可以多次赔付吗");
//        list2.add("那可以赔几次呢");
//        list2.add("我几次病可以一直陪吗");
//        list2.add("最多能够给个几次");
//        list2.add("最多能够给个三次");
//        list2.add("最多能够给个两次");
//        list2.add("最多能够给个五次");
//        list2.add("最多能赔几次");
//        list2.add("这个保险报了，那就不能重复了吗");
//        list2.add("你这种分三次是什么意思");
//        list2.add("他为什么赔付三次呀");
//        list2.add("你们假如一次性理赔之后，我再还能续保吗");
//        TFIDFAlgorithm tfidfAlgorithm = new TFIDFAlgorithm();
//        List<Term> terms = tfidfAlgorithm.parse(StandardQuestion);
//
//        Double[][] features = new Double[list1.size() + list2.size()][];
//        Double[] targetValues = new Double[list1.size() + list2.size()];
//
//        int count = list1.size();
//
//        for (int i = 0; i < list1.size(); i++) {
//            List<Term> termsTest = ToAnalysis.parse(list1.get(i)).getTerms();
//            List<Double> doublesList = new ArrayList<>();
//            for(Term t : termsTest){
//                doublesList.add(tfidfAlgorithm.computeTF(t.getName(), terms));
//            }
//            features[i] = doublesList.toArray(new Double[doublesList.size()]);
//            targetValues[i] = 1.0;
//        }
//
//        for (int i = 0; i < list2.size(); i++) {
//            List<Term> termsTest = ToAnalysis.parse(list2.get(i)).getTerms();
//            List<Double> doublesList = new ArrayList<>();
//            for(Term t : termsTest){
//                doublesList.add(tfidfAlgorithm.computeTF(t.getName(), terms));
//            }
//            features[i+count] = doublesList.toArray(new Double[doublesList.size()]);
//            targetValues[i+count] = 2.0;
//        }
//
//
//        System.out.println("end");

//        /** 1、构造样本 **/
//        final int sampleNum = 100;//样本数量
//        double[][] features = new double[sampleNum][];//特征向量数组 本例中即xy坐标构成的向量
//        double[] targetValues = new double[sampleNum];//分类值数组 本例中即点属于哪个象限
//        Random random = new Random(233);
//        for (int i = 0; i < sampleNum; i++) {
//            //每次循环随机生成一条样本数据
//            //随机在[-100,100)直接生成x、y坐标
//            double x = random.nextInt(200) - 100;
//            double y = random.nextInt(200) - 100;
//            int quadrant = getQuadrant(x, y);//样本分类，实际应用中分类一般需要手工录入，本例中获取象限有公式我们就自己生成了
//            //坐标值归一化，特征向量的值需要在[0,1]间，所以需要归一化
//            double normalizationX = (x + 100) / 200;
//            double normalizationY = (y + 100) / 200;
//            //把特征向量和分类值丢到数组里
//            features[i] = new double[]{normalizationX, normalizationY};
//            targetValues[i] = quadrant;
//        }
//
//        /** 2、训练模型 **/
//        svm_model model = train(features, targetValues);
//
//        /** 3、识别分类 **/
//        //待识别向量
//        svm_node[] test = new svm_node[]{new svm_node(), new svm_node()};
//        test[0].index = 1;
//        test[0].value = -45.5;
//        test[1].index = 2;
//        test[1].value = -20.2;
//        //归一化
//        test[0].value = (test[0].value + 100) / 200;
//        test[1].value = (test[1].value + 100) / 200;
//
//        double result = svm.svm_predict(model, test);    // 不带概率的分类测试
//        System.out.println("所在象限 " + result);//所在象限 3.0
//
//        double[] l = new double[4];
//        double result_prob = svm.svm_predict_probability(model, test, l);        //带预测概率的分类测试
//        System.out.println("Result with prob " + result_prob);
//        System.out.println("Probability " + l[0] + "\t" + l[1]);
    }

    /**
     * 训练模型
     *
     * @param features
     * @param targetValues
     * @return
     */
    private static svm_model train(double[][] features, double[] targetValues) {
        svm_node[][] svmNodes = new svm_node[features.length][features[0].length];
        for (int i = 0; i < features.length; i++) {
            double[] feature = features[i];
            for (int i1 = 0; i1 < feature.length; i1++) {
                svm_node svmNode = new svm_node();
                svmNode.index = i1 + 1;//index从1开始，所以要+1
                svmNode.value = feature[i1];
                svmNodes[i][i1] = svmNode;
            }
        }

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
     * 4  |  1
     * ----|----
     * 3  |  2
     */
    private static int getQuadrant(double x, double y) {
        if (x > 0) {
            if (y > 0) {
                return 1;
            } else {
                return 2;
            }
        } else {
            if (y > 0) {
                return 4;
            } else {
                return 3;
            }
        }
    }
}
