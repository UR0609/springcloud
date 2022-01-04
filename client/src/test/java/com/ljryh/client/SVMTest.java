//package com.ljryh.client;
//
//import com.ljryh.client.entity.svm.ExcelData;
//import com.ljryh.client.utils.RedisUtils;
//import com.ljryh.client.utils.TFIDFAlgorithm;
//import com.ljryh.client.utils.TXTUtils;
//import libsvm.*;
//import org.ansj.domain.Term;
//import org.ansj.recognition.impl.StopRecognition;
//import org.ansj.splitWord.analysis.ToAnalysis;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.io.FileInputStream;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author ljryh
// * @date 2021/11/2 10:12
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class SVMTest {
//
//    @Resource
//    private RedisUtils redisUtils;
//
//    private String StandardQuestion = "你这个保险只赔疾病吗?" +
//            "只有生病给赔吗" +
//            "意外伤亡也一样给保吗" +
//            "意外保吗" +
//            "保障范围是什么" +
//            "你们这个是针对疾病吗" +
//            "你们这个是针对肿瘤吗" +
//            "你们这个是意外险" +
//            "你们这个是重疾险" +
//            "除了疾病之外，还保什么吗"
//            + "我生病可以多次赔付吗" +
//            "那可以赔几次呢" +
//            "我几次病可以一直陪吗" +
//            "能一直赔吗" +
//            "最多能够给个几次" +
//            "最多能够给个三次" +
//            "最多能够给个两次" +
//            "最多能够给个五次" +
//            "最多能赔几次" +
//            "这个保险报了，那就不能重复了吗" +
//            "你这种分三次是什么意思?" +
//            "他为什么赔付三次呀" +
//            "你们假如一次性理赔之后，我再还能续保吗";
//
//
//    @Test
//    public void SVMTest() throws Exception {
//        StopRecognition filter = new StopRecognition();
//        filter.insertStopWords(TXTUtils.getStopWords()); //过滤单词
//
//        List<ExcelData> list = new ArrayList<>();
//
//        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("D:\\20210412075958.xlsx"));
//        //读取第2个工作表
//        XSSFSheet sheet = xssfWorkbook.getSheetAt(1);
//        //获取最后一行的num，即总行数。此处从0开始
//        int maxRow = sheet.getLastRowNum();
//        for (int row = 1; row <= maxRow; row++) {
//            ExcelData excelData = new ExcelData(sheet.getRow(row).getCell(0).toString(),
//                    sheet.getRow(row).getCell(1).toString());
//            list.add(excelData);
//
////            String example = sheet.getRow(row).getCell(1).toString();
////            List<Term> terms = ToAnalysis.parse(example).recognition(filter).getTerms();
////            for(Term t : terms){
////                String name = wordChangeUnicode(t.getName());
////                Object dataCount = redisUtils.get("svm:data:count:" + name);
////                Object data = redisUtils.get("svm:data:" + name);
////                // 记录分词索引
////                if(data == null){
////                    int redisDataCount;
////                    if (redisUtils.get("svm:count") == null) {
////                        redisUtils.set("svm:count", 1);
////                        redisDataCount = 0;
////                    } else {
////                        redisDataCount = Integer.parseInt(redisUtils.get("svm:count").toString());
////                    }
////                    redisUtils.set("svm:data:" + name, redisDataCount++);
////                    redisUtils.set("svm:count", redisDataCount);
////                }
////                // 记录出现的频率
////                if(dataCount == null){
////                    redisUtils.set("svm:data:count:" + name,1);
////                } else {
////                    redisUtils.set("svm:data:count:" + name,Integer.parseInt(dataCount.toString()) + 1);
////                }
////            }
////            // 记录词语总量
////            Object obj = redisUtils.get("svm:total:count");
////            if(obj == null){
////                redisUtils.set("svm:total:count",terms.size());
////            } else {
////                redisUtils.set("svm:total:count",Integer.parseInt(obj.toString()) + terms.size());
////            }
//        }
//        List<List<svm_node>> testList = new ArrayList<>();
//        double[] targetValues = new double[list.size()];
//        int count = 0;
//
//        for (ExcelData excelData : list) {
//            List<svm_node> svmNodeList = new ArrayList<>();
//            List<Term> terms = ToAnalysis.parse(excelData.getExample()).recognition(filter).getTerms();
//            for (Term t : terms) {
//                svm_node svmNode = new svm_node();
//                String name = wordChangeUnicode(t.getName());
//                if (redisUtils.get("svm:data:" + name) != null) {
//                    svmNode.index = Integer.parseInt(redisUtils.get("svm:data:" + name).toString());
//                    svmNode.value = (double) Integer.parseInt(redisUtils.get("svm:data:count:" + name).toString())
//                            / Integer.parseInt(redisUtils.get("svm:total:count").toString());
//                    svmNodeList.add(svmNode);
//                }
//            }
//            testList.add(svmNodeList);
//
//            Object intentionData = redisUtils.get("svm:intention:data:" + excelData.getIntention());
//
//            if (intentionData == null) {
//                Object obj = redisUtils.get("svm:intention:count");
//                if (obj == null) {
//                    targetValues[count] = 1.0;
//                    redisUtils.set("svm:intention:count", 1.0);
//                    redisUtils.set("svm:intention:data:" + excelData.getIntention(), 1.0);
//                } else {
//                    targetValues[count] = Double.parseDouble(obj.toString()) + 1;
//                    redisUtils.set("svm:intention:count", Double.parseDouble(obj.toString()) + 1);
//                    redisUtils.set("svm:intention:data:" + excelData.getIntention(), Double.parseDouble(obj.toString()) + 1);
//                }
//            } else {
//                targetValues[count] = Double.parseDouble(intentionData.toString());
//            }
//            count++;
//        }
//        svm_node[][] svmNodes = listToSvmNode(testList);
//        svm_problem sp = new svm_problem();
//        sp.x = svmNodes;
//        sp.y = targetValues;
//        sp.l = count - 1;
//
//        //调参什么的，太深奥了，先用默认值好了
//        svm_parameter prm = new svm_parameter();
//        prm.svm_type = svm_parameter.C_SVC;
//        prm.kernel_type = svm_parameter.RBF;
//        prm.C = 1000;
//        prm.eps = 0.0000001;
//        prm.gamma = 10;
//        prm.probability = 1;
//        prm.cache_size = 1024;
//
//        svm_model model = svm.svm_train(sp, prm);
//
////        String test = "开会呢，你别给我打电话了";
//        String test = "我刷卡就是为了提额,不办";
//        List<Term> termsTest = ToAnalysis.parse(test).recognition(filter).getTerms();
//        List<svm_node> svmNodeList = new ArrayList<>();
//        for (Term t : termsTest) {
//            svm_node svmNode = new svm_node();
//            String name = wordChangeUnicode(t.getName());
//            if (redisUtils.get("svm:data:" + name) != null) {
//                svmNode.index = Integer.parseInt(redisUtils.get("svm:data:" + name).toString());
//                svmNode.value = (double) Integer.parseInt(redisUtils.get("svm:data:count:" + name).toString())
//                        / Integer.parseInt(redisUtils.get("svm:total:count").toString());
//                svmNodeList.add(svmNode);
//            }
//        }
//        svm_node[] svmNodesTest = svmNodeList.toArray(new svm_node[svmNodeList.size()]);
//
//        double result = svm.svm_predict(model, svmNodesTest);    // 不带概率的分类测试
//        System.out.println("所在象限 " + result);//所在象限 3.0
//        double[] l = new double[24];
//        double result_prob = svm.svm_predict_probability(model, svmNodesTest, l);        //带预测概率的分类测试
//        System.out.println("Result with prob " + result_prob);
//
//        for (int i = 1; i <= 24; i++) {
//            System.out.println("Result  " + i + ":" + l[i - 1]);
//        }
//
//        System.out.println(list.size());
//
////        svm();
//    }
//
//    private void svm() {
//        // 只保疾病吗
//        List<String> list1 = getList1();
//        // 最多可以赔几次
//        List<String> list2 = getList2();
//
//
//        TFIDFAlgorithm tfidfAlgorithm = new TFIDFAlgorithm();
//        List<Term> terms = tfidfAlgorithm.parse(StandardQuestion);
//        termsSaveRedis(terms);
//
//        double[] targetValues = new double[list1.size() + list2.size()];
//        int count = 0;
//        List<List<svm_node>> testList = new ArrayList<>();
//        for (String s : list1) {
//            List<svm_node> svmNodeList = new ArrayList<>();
//            List<Term> termsTest = ToAnalysis.parse(s).getTerms();
//            for (Term t : termsTest) {
//                svm_node svmNode = new svm_node();
//                String name = wordChangeUnicode(t.getName());
//                if (redisUtils.get("svm:data:" + name) != null) {
//                    svmNode.index = Integer.parseInt(redisUtils.get("svm:data:" + name).toString());
//                    svmNode.value = tfidfAlgorithm.computeTF(t.getName(), terms);
//                    svmNodeList.add(svmNode);
//                }
//            }
//            testList.add(svmNodeList);
//            targetValues[count] = 1.0;
//            count++;
//        }
//
//        for (String s : list2) {
//            List<svm_node> svmNodeList = new ArrayList<>();
//            List<Term> termsTest = ToAnalysis.parse(s).getTerms();
//            for (Term t : termsTest) {
//                svm_node svmNode = new svm_node();
//                String name = wordChangeUnicode(t.getName());
//                if (redisUtils.get("svm:data:" + name) != null) {
//                    svmNode.index = Integer.parseInt(redisUtils.get("svm:data:" + name).toString());
//                    svmNode.value = tfidfAlgorithm.computeTF(t.getName(), terms);
//                    svmNodeList.add(svmNode);
//                }
//            }
//            testList.add(svmNodeList);
//            targetValues[count] = 2.0;
//            count++;
//        }
//        svm_node[][] svmNodes = listToSvmNode(testList);
//        svm_problem sp = new svm_problem();
//        sp.x = svmNodes;
//        sp.y = targetValues;
//        sp.l = count - 1;
//
//        //调参什么的，太深奥了，先用默认值好了
//        svm_parameter prm = new svm_parameter();
//        prm.svm_type = svm_parameter.C_SVC;
//        prm.kernel_type = svm_parameter.RBF;
//        prm.C = 1000;
//        prm.eps = 0.0000001;
//        prm.gamma = 10;
//        prm.probability = 1;
//        prm.cache_size = 1024;
//
//        svm_model model = svm.svm_train(sp, prm);
//
//        String test = "你这个保险赔什么";
//
//        List<Term> termsTest = ToAnalysis.parse(test).getTerms();
//
//        List<svm_node> svmNodeList = new ArrayList<>();
//        for (Term t : termsTest) {
//            svm_node svmNode = new svm_node();
//            String name = wordChangeUnicode(t.getName());
//            if (redisUtils.get("svm:data:" + name) != null) {
//                svmNode.index = Integer.parseInt(redisUtils.get("svm:data:" + name).toString());
//                svmNode.value = tfidfAlgorithm.computeTF(t.getName(), terms);
//                svmNodeList.add(svmNode);
//            }
//        }
//        svm_node[] svmNodesTest = svmNodeList.toArray(new svm_node[svmNodeList.size()]);
//
//        double result = svm.svm_predict(model, svmNodesTest);    // 不带概率的分类测试
//        System.out.println("所在象限 " + result);//所在象限 3.0
//
//        double[] l = new double[4];
//        double result_prob = svm.svm_predict_probability(model, svmNodesTest, l);        //带预测概率的分类测试
//        System.out.println("Result with prob " + result_prob);
//        System.out.println("Probability " + l[0] + "\t" + l[1]);
//    }
//
//    private static svm_node[][] listToSvmNode(List<List<svm_node>> list) {
//
//        svm_node[][] svmNodes = new svm_node[list.size()][];
//        int i = 0;
//        for (List<svm_node> svm_nodeList : list) {
//            svmNodes[i] = svm_nodeList.toArray(new svm_node[svm_nodeList.size()]);
//            i++;
//        }
//
//        return svmNodes;
//    }
//
//    private void termsSaveRedis(List<Term> terms) {
//        for (Term term : terms) {
//            String name = wordChangeUnicode(term.getName());
//            Object redisData = redisUtils.get("svm:data:" + name);
//            if (redisData == null) {
//                int redisDataCount;
//                if (redisUtils.get("svm:count") == null) {
//                    redisUtils.set("svm:count", 1);
//                    redisDataCount = 0;
//                } else {
//                    redisDataCount = Integer.parseInt(redisUtils.get("svm:count").toString());
//                }
//                redisUtils.set("svm:data:" + name, redisDataCount++);
//                redisUtils.set("svm:count", redisDataCount);
//            }
//        }
//    }
//
//    private String wordChangeUnicode(String s) {
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//            if (19968 <= c && c < 40869) {
//                sb.append("\\u" + Integer.toHexString(c));
//            } else {
//                sb.append(c);
//            }
//
//        }
//        return sb.toString();
//    }
//
//    private List<String> getList1() {
//        // 只保疾病吗
//        List<String> list = new ArrayList<>();
//        list.add("你这个保险只赔疾病吗");
//        list.add("只有生病给赔吗");
//        list.add("意外伤亡也一样给保吗");
//        list.add("意外保吗");
//        list.add("保障范围是什么");
//        list.add("你们这个是针对疾病吗");
//        list.add("你们这个是针对肿瘤吗");
//        list.add("你们这个是意外险");
//        list.add("你们这个是重疾险");
//        list.add("除了疾病之外，还保什么吗");
//        return list;
//    }
//
//    private List<String> getList2() {
//        // 只保疾病吗
//        List<String> list = new ArrayList<>();
//        list.add("我生病可以多次赔付吗");
//        list.add("那可以赔几次呢");
//        list.add("我几次病可以一直陪吗");
//        list.add("最多能够给个几次");
//        list.add("最多能够给个三次");
//        list.add("最多能够给个两次");
//        list.add("最多能够给个五次");
//        list.add("最多能赔几次");
//        list.add("这个保险报了，那就不能重复了吗");
//        list.add("你这种分三次是什么意思");
//        list.add("他为什么赔付三次呀");
//        list.add("你们假如一次性理赔之后，我再还能续保吗");
//        return list;
//    }
//}
