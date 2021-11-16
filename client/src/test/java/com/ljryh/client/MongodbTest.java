package com.ljryh.client;

import com.ljryh.client.mapper.ChildrenMapper;
import com.ljryh.client.utils.MongodbUtils;
import com.ljryh.client.utils.TFIDFAlgorithm;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbTest {

    @Resource
    private ChildrenMapper childrenMapper;

    // git 无法提交 重置本机git设置 git config --global credential.helper store

    private void test1(){
        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("BatchNo", "20210707001");
        List<Map> mongoList = MongodbUtils.find(map, Map.class, "MD_cmbcdc_cmbc_last");
        //获取开始时间
        long startTime=System.currentTimeMillis();


//        BigDecimal repaymentAmountTotal = new BigDecimal("0.0");
//        BigDecimal owedAmountTotal = new BigDecimal("0.0");
//
//        for(Map<String, String> dataMap : mongoList){
//            if(!"M13+".equals(dataMap.get("OverdueInterval"))){
//                if(Double.parseDouble(dataMap.get("CasePay")) > 0){
//                    repaymentAmountTotal = repaymentAmountTotal.add(new BigDecimal(dataMap.get("CasePay")));
//                }
//                owedAmountTotal = owedAmountTotal.add(new BigDecimal(dataMap.get("CaseAmount")));
//            }
//        }

//        Double sum = mongoList.stream().mapToDouble(m -> Double.parseDouble(m.get("CaseAmount").toString())).sum();
        BigDecimal repaymentAmountTotal = new BigDecimal("0.0");
        if(mongoList != null){
            repaymentAmountTotal = this.getRepaymentAmount(mongoList);
        }
        BigDecimal owedAmountTotal = this.getOwedAmount(mongoList);

        System.out.println("在案金额:"+owedAmountTotal+",真实还款:"+repaymentAmountTotal+",流出率:"+repaymentAmountTotal.divide(owedAmountTotal, 4, BigDecimal.ROUND_HALF_UP));
//        System.out.println(result2);

//        Map<Object ,List<Map>> groupMap = mongoList.stream().filter(item -> Double.parseDouble((String) item.get("CasePay")) > 0).collect(Collectors.groupingBy(items -> items.get("BillDate")));
        Map<Object, List<Map>> groupMap = mongoList.stream().collect(Collectors.groupingBy(items -> items.get("BillDate")));

        for (Map.Entry<Object, List<Map>> entry : groupMap.entrySet()) {
            BigDecimal repaymentAmountTotalDay = this.getRepaymentAmount(entry.getValue());
            BigDecimal owedAmountTotalDay = this.getOwedAmount(entry.getValue());
            System.out.println("还款日"+entry.getKey()+",在案金额:"+owedAmountTotalDay+",真实还款:"+repaymentAmountTotalDay+",流出率:"+repaymentAmountTotalDay.divide(owedAmountTotalDay, 4, BigDecimal.ROUND_HALF_UP));
//            System.out.println("key:value = " + entry.getKey() + ":" + entry.getValue());
        }

        // 最后还款日 LastPayDate
        // 还款日 BillDate
        // 跑批截至日期 CaseEndTime

        // 用 最后还款日 比 跑批截至日期 判断 是降一还是降二  还款日分组

        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
    }
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
    @Test
    public void testSave1() throws Exception {

        TFIDFAlgorithm tfidfAlgorithm = new TFIDFAlgorithm();
//        String filePath = "/Users/lionel/PycharmProjects/python-app/com/pythonapp/spider/output.txt";
        String url = "http://baike.baidu.com/item/Java/85979";
        List<Term> termsTest = ToAnalysis.parse("我能全额还款，我有钱").getTerms();
        List<Term> terms = tfidfAlgorithm.parse("之前办理过分期，现在不想办了\n" +
                "可以提前还款吗？收违约金吗？\n" +
                "分期后还有利息吗？分期怎么收利息/分期收多少利息？\n" +
                "分期手续费太高了，不想办理\n" +
                "我能全额还款，我有钱");
        for(Term t : termsTest){
            System.out.println("[【" + t.getName() + "】词频 ] " + tfidfAlgorithm.computeTF(t.getName(), terms));
        }
//        String word = "语言";
//        List<Term> terms = tfidfAlgorithm.parse(tfidfAlgorithm.getTextFromUrl(url));
//        System.out.println("[【" + word + "】词频 ] " + tfidfAlgorithm.computeTF(word, terms));
//        System.out.println("[【" + word + "】逆文档频率 ] " + tfidfAlgorithm.computeIDF(filePath, word));
//        System.out.println("[【" + word + "】词频-逆文档频率 ] "+tfidfAlgorithm.computeTFIDF(filePath,terms,word));

//        System.out.println(ImportExcelUtil.getUsers("C:\\Users\\Administrator\\Desktop\\test.xlsx"));


//        String data = "[{\"col15\":\"2021072101000\",\"col14\":\"爱聚会\",\"col13\":\"有\",\"col12\":\"教师\",\"col11\":\"已婚无子女\",\"col8\":\"46-55岁\",\"col9\":\"24-60万元\",\"col6\":\"15002455700\",\"col10\":\"比较健康，体检记录有部分异常\",\"col7\":\"中班10号\",\"col4\":\"徐丹\",\"col5\":\"1\",\"col2\":\"2021/4/19\",\"col3\":\"100000\",\"col1\":\"2021072101000\"},{\"col15\":\"2021072101001\",\"col14\":\"爱健身\",\"col13\":\"无\",\"col12\":\"公务员\",\"col11\":\"已婚有子女\",\"col8\":\"25岁以下\",\"col9\":\"12万元以下\",\"col6\":\"13507210101\",\"col10\":\"非常健康，完全没有问题\",\"col7\":\"中班1号\",\"col4\":\"张军营\",\"col5\":\"0\",\"col2\":\"2021/4/10\",\"col3\":\"10000\",\"col1\":\"2021072101001\"},{\"col15\":\"2021072101002\",\"col14\":\"爱健身\",\"col13\":\"无\",\"col12\":\"企事业单位\",\"col11\":\"已婚有子女\",\"col8\":\"25岁以下\",\"col9\":\"12-24万元\",\"col6\":\"13507210102\",\"col10\":\"非常健康，完全没有问题\",\"col7\":\"中班2号\",\"col4\":\"郝苑芙\",\"col5\":\"0\",\"col2\":\"2021/4/11\",\"col3\":\"200000\",\"col1\":\"2021072101002\"},{\"col15\":\"2021072101003\",\"col14\":\"中天嘉华-自测链接\",\"col13\":\"无\",\"col12\":\"学生\",\"col11\":\"已婚有子女\",\"col8\":\"25岁以下\",\"col9\":\"12-24万元\",\"col6\":\"13507210103\",\"col10\":\"非常健康，完全没有问题\",\"col7\":\"中班3号\",\"col4\":\"倪长欣\",\"col5\":\"0\",\"col2\":\"2021/4/12\",\"col3\":\"300000\",\"col1\":\"2021072101003\"},{\"col15\":\"2021072101004\",\"col14\":\"爱旅游\",\"col13\":\"无\",\"col12\":\"公务员\",\"col11\":\"单身\",\"col8\":\"25-35岁\",\"col9\":\"24-60万元\",\"col6\":\"13507210104\",\"col10\":\"非常健康，完全没有问题\",\"col7\":\"中班4号\",\"col4\":\"陈雷\",\"col5\":\"0\",\"col2\":\"2021/4/13\",\"col3\":\"200000\",\"col1\":\"2021072101004\"},{\"col15\":\"2021072101005\",\"col14\":\"爱旅游\",\"col13\":\"房贷100万，车贷10万\",\"col12\":\"企事业单位\",\"col11\":\"单身\",\"col8\":\"46-55岁\",\"col9\":\"120万元以上\",\"col6\":\"13507210105\",\"col10\":\"亚健康，有既往病史\",\"col7\":\"中班5号\",\"col4\":\"刘正伟\",\"col5\":\"1\",\"col2\":\"2021/4/14\",\"col3\":\"200000\",\"col1\":\"2021072101005\"},{\"col15\":\"2021072101006\",\"col14\":\"爱旅游\",\"col13\":\"房贷100万，车贷10万，其他贷款200万\",\"col12\":\"教师\",\"col11\":\"单身\",\"col8\":\"55岁以上\",\"col9\":\"60-120万元\",\"col6\":\"13507210106\",\"col10\":\"亚健康，有既往病史\",\"col7\":\"中班6号\",\"col4\":\"王慧\",\"col5\":\"0\",\"col2\":\"2021/4/15\",\"col3\":\"100000\",\"col1\":\"2021072101006\"},{\"col15\":\"2021072101007\",\"col14\":\"爱旅游\",\"col13\":\"无\",\"col12\":\"运动员\",\"col11\":\"单身\",\"col8\":\"25-35岁\",\"col9\":\"12-24万元\",\"col6\":\"13507210107\",\"col10\":\"亚健康，有既往病史\",\"col7\":\"中班7号\",\"col4\":\"毕治华\",\"col5\":\"1\",\"col2\":\"2021/4/16\",\"col3\":\"200000\",\"col1\":\"2021072101007\"},{\"col15\":\"2021072101008\",\"col14\":\"爱聚会\",\"col13\":\"有\",\"col12\":\"医护\",\"col11\":\"已婚无子女\",\"col8\":\"55岁以上\",\"col9\":\"60-120万元\",\"col6\":\"13507210108\",\"col10\":\"比较健康，体检记录有部分异常\",\"col7\":\"中班8号\",\"col4\":\"董凯\",\"col5\":\"0\",\"col2\":\"2021/4/17\",\"col3\":\"200000\",\"col1\":\"2021072101008\"},{\"col15\":\"2021072101009\",\"col14\":\"爱聚会\",\"col13\":\"无\",\"col12\":\"医护\",\"col11\":\"已婚无子女\",\"col8\":\"36-45岁\",\"col9\":\"12万元以下\",\"col6\":\"13507210109\",\"col10\":\"比较健康，体检记录有部分异常\",\"col7\":\"中班9号\",\"col4\":\"徐健\",\"col5\":\"0\",\"col2\":\"2021/4/18\",\"col3\":\"100000\",\"col1\":\"2021072101009\"},{\"col15\":\"2021072101010\",\"col14\":\"中天嘉华-自测链接\",\"col13\":\"无\",\"col12\":\"学生\",\"col11\":\"已婚有子女\",\"col8\":\"25岁以下\",\"col9\":\"12-24万元\",\"col6\":\"13507210110\",\"col10\":\"非常健康，完全没有问题\",\"col7\":\"中班10号\",\"col4\":\"赵若汐\",\"col5\":\"1\",\"col2\":\"2021/4/19\",\"col3\":\"100000\",\"col1\":\"2021072101010\"},{\"col15\":\"2021072101011\",\"col14\":\"爱旅游\",\"col13\":\"无\",\"col12\":\"公务员\",\"col11\":\"单身\",\"col8\":\"25岁以下\",\"col9\":\"12-24万元\",\"col6\":\"13507210111\",\"col10\":\"非常健康，完全没有问题\",\"col7\":\"中班11号\",\"col4\":\"袁歆瑶\",\"col5\":\"0\",\"col2\":\"2021/4/20\",\"col3\":\"10000\",\"col1\":\"2021072101011\"},{\"col15\":\"2021072101012\",\"col14\":\"爱旅游\",\"col13\":\"房贷100万，车贷10万\",\"col12\":\"企事业单位\",\"col11\":\"单身\",\"col8\":\"25岁以下\",\"col9\":\"24-60万元\",\"col6\":\"13507210112\",\"col10\":\"亚健康，有既往病史\",\"col7\":\"中班12号\",\"col4\":\"张嘉睿\",\"col5\":\"0\",\"col2\":\"2021/4/21\",\"col3\":\"200000\",\"col1\":\"2021072101012\"},{\"col15\":\"2021072101013\",\"col14\":\"爱旅游\",\"col13\":\"房贷100万，车贷10万，其他贷款200万\",\"col12\":\"教师\",\"col11\":\"单身\",\"col8\":\"25岁以下\",\"col9\":\"120万元以上\",\"col6\":\"13507210113\",\"col10\":\"亚健康，有既往病史\",\"col7\":\"中班13号\",\"col4\":\"李佳沐\",\"col5\":\"0\",\"col2\":\"2021/4/22\",\"col3\":\"300000\",\"col1\":\"2021072101013\"},{\"col15\":\"2021072101014\",\"col14\":\"爱旅游\",\"col13\":\"无\",\"col12\":\"运动员\",\"col11\":\"单身\",\"col8\":\"25岁以下\",\"col9\":\"60-120万元\",\"col6\":\"13507210114\",\"col10\":\"亚健康，有既往病史\",\"col7\":\"中班14号\",\"col4\":\"王潇北\",\"col5\":\"0\",\"col2\":\"2021/4/23\",\"col3\":\"200000\",\"col1\":\"2021072101014\"},{\"col15\":\"2021072101015\",\"col14\":\"爱聚会\",\"col13\":\"有\",\"col12\":\"医护\",\"col11\":\"已婚无子女\",\"col8\":\"25岁以下\",\"col9\":\"12-24万元\",\"col6\":\"13507210115\",\"col10\":\"比较健康，体检记录有部分异常\",\"col7\":\"中班15号\",\"col4\":\"李悠然\",\"col5\":\"1\",\"col2\":\"2021/4/24\",\"col3\":\"200000\",\"col1\":\"2021072101015\"},{\"col15\":\"2021072101016\",\"col14\":\"中天嘉华-自测链接\",\"col13\":\"无\",\"col12\":\"学生\",\"col11\":\"已婚有子女\",\"col8\":\"25-35岁\",\"col9\":\"12-24万元\",\"col6\":\"13507210116\",\"col10\":\"非常健康，完全没有问题\",\"col7\":\"中班16号\",\"col4\":\"高渤勋\",\"col5\":\"0\",\"col2\":\"2021/4/25\",\"col3\":\"100000\",\"col1\":\"2021072101016\"},{\"col15\":\"2021072101017\",\"col14\":\"爱旅游\",\"col13\":\"无\",\"col12\":\"公务员\",\"col11\":\"单身\",\"col8\":\"46-55岁\",\"col9\":\"12-24万元\",\"col6\":\"13507210117\",\"col10\":\"非常健康，完全没有问题\",\"col7\":\"中班17号\",\"col4\":\"高樱洛\",\"col5\":\"1\",\"col2\":\"2021/4/26\",\"col3\":\"200000\",\"col1\":\"2021072101017\"},{\"col15\":\"2021072101018\",\"col14\":\"爱旅游\",\"col13\":\"房贷100万，车贷10万\",\"col12\":\"企事业单位\",\"col11\":\"单身\",\"col8\":\"55岁以上\",\"col9\":\"24-60万元\",\"col6\":\"13507210118\",\"col10\":\"亚健康，有既往病史\",\"col7\":\"中班18号\",\"col4\":\"顾清滢\",\"col5\":\"0\",\"col2\":\"2021/4/27\",\"col3\":\"200000\",\"col1\":\"2021072101018\"},{\"col15\":\"2021072101019\",\"col14\":\"爱旅游\",\"col13\":\"房贷100万，车贷10万，其他贷款200万\",\"col12\":\"教师\",\"col11\":\"单身\",\"col8\":\"25-35岁\",\"col9\":\"120万元以上\",\"col6\":\"13507210119\",\"col10\":\"亚健康，有既往病史\",\"col7\":\"中班19号\",\"col4\":\"罗一寒\",\"col5\":\"0\",\"col2\":\"2021/4/28\",\"col3\":\"100000\",\"col1\":\"2021072101019\"},{\"col15\":\"2021072101020\",\"col14\":\"爱旅游\",\"col13\":\"无\",\"col12\":\"运动员\",\"col11\":\"单身\",\"col8\":\"55岁以上\",\"col9\":\"60-120万元\",\"col6\":\"15002455700\",\"col10\":\"亚健康，有既往病史\",\"col7\":\"中班20号\",\"col4\":\"梁嘉泽\",\"col5\":\"1\",\"col2\":\"2021/4/29\",\"col3\":\"200000\",\"col1\":\"2021072101020\"}]";
//
//        List<Map> list =  JacksonUtil.jsonToList(data,Map.class);
//
//        List<Map> unique = list.stream().filter(distinctByKey(m -> m.get("col6"))).collect(Collectors.toList());
//
//        System.out.println(123);

//        test1();
//        Map<Object, Object> map1 = new ConcurrentHashMap<>();
//        map1.put("1","1");
//        map1.put("2","2");
//        map1.put("3","3");
//        map1.put("4","4");
//        map1.put("5","5");
//        Map<Object, Object> map2 = new ConcurrentHashMap<>();
//        map2.put("5","5");
//        map2.put("6","6");
//        map2.put("7","7");
//        map2.put("8","8");
//        map2.put("9","9");
//
//        Set<Object> keySet = new HashSet<>();
//
//        Set<Object> keySet1 = map1.keySet();
//        Set<Object> keySet2 = map2.keySet();
//
//        System.out.println(keySet1);
//        System.out.println(keySet2);
//
//        keySet.addAll(keySet1);
//        keySet.addAll(keySet2);
//        System.out.println(keySet);
//
//        Object o = map2.get("1");
//        if(o==null){
//            System.out.println("空");
//        }

//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//        System.out.println(dateFormat.format(date));

//        MongodbUtils.removeAll("UserMongo");


//        UserMongo userMongo = new UserMongo("admin");
//        MongodbUtils.save(userMongo);
//        System.out.println(123);
//        MongodbTestModel mo = new MongodbTestModel();
//        for (int i = 0; i < 100; i++) {
//            mo.setMid(i + "");
//            mo.setName("MongodbTestModel" + i);
//            mo.setAge("22");
//            MongodbUtils.save(mo);
//        }

//        Map<String,Object> map = new ConcurrentHashMap<>();
////
//        map.put("mid","1");
//
//        List<MongodbTestModel> list =  MongodbUtils.findAll(MongodbTestModel.class,"mongodbTestModel");
//
//        MongodbTestModel mongodbTestModel = MongodbUtils.findOne(MongodbTestModel.class,map);
////
////
//        System.out.println(123);

//        List<Map> list =  MongodbUtils.findAll(Map.class,"MD_cmbcdc_cmbc");
//        Map<String, Object> map = new ConcurrentHashMap<>();
//        map.put("BatchNo","20210222007");
//        List<Map> list1 =  MongodbUtils.find(map,Map.class,"MD_cmbcdc_cmbc");
//        map.put("BatchNo","20210222008");
//        List<Map> list2 =  MongodbUtils.find(map,Map.class,"MD_cmbcdc_cmbc");
//
//        System.out.println(list.size());

//        Map<String,Object> map = new ConcurrentHashMap<>();
//        map.put("name","a");
////        childrenMapper.updateEntityByMap(map);
//
////        childrenMapper.updateTest(map);
//        childrenMapper.updateTest2(map);
//
//        System.out.println(123);
    }

    // 获取还款总数
    private BigDecimal getRepaymentAmount(List<Map> list) {
        return list.stream()
                /**
                 * 降 M13+ 与 不还款的过滤掉
                 */
                .filter(item -> !"M13+".equals(item.get("OverdueInterval")) && Double.parseDouble(item.get("CasePay").toString()) > 0)
                // 将user对象的mongey取出来map为Bigdecimal
                .map(m -> {
                    /**
                     * 最后还款日 LastPayDate
                     * 跑批截至日期 CaseEndTime
                     * 还款日 BillDate
                     * 用 最后还款日 比 跑批截至日期 判断 是降一还是降二  还款日分组
                     */
                    // 判断是降一还是降二
                    if (this.isThisTime(m.get("LastPayDate").toString(), m.get("CaseEndTime").toString())) {
                        // 还款金额
                        BigDecimal CasePay = new BigDecimal(String.valueOf(m.get("CasePay")));
                        // 降一
                        BigDecimal OneAmount = new BigDecimal(String.valueOf(m.get("OneAmount")));
                        // 本金
                        BigDecimal CasePrincipal = new BigDecimal(String.valueOf(m.get("CasePrincipal")));
                        /**
                         * 如果还款金额大于降一
                         * 使用降一 (降一不为零时 使用降一 若降一为0 返回本金 )
                         * 否则还款金额
                         */
                        return CasePay.compareTo(OneAmount) == 1
                                ? (OneAmount.compareTo(BigDecimal.ZERO) == 0 ? CasePrincipal : OneAmount)
                                : CasePay;
//                        /**
//                         * 如果还款金额大于降一
//                         * 使用降一 (降一不为零时 使用降一 若降一为0 判断本金与还款金额 还款金额大于本金 返回本金 否则 还款金额)
//                         * 否则还款金额
//                         */
//                        return CasePay.compareTo(OneAmount) == 1
//                                ? (OneAmount.compareTo(BigDecimal.ZERO) == 0 ? (CasePay.compareTo(CasePrincipal) == 1 ? CasePrincipal : CasePay) : OneAmount)
//                                : CasePay;
                    } else {
                        BigDecimal CasePay = new BigDecimal(String.valueOf(m.get("CasePay")));
                        BigDecimal TwoAmount = new BigDecimal(String.valueOf(m.get("TwoAmount")));
                        // 本金
                        BigDecimal CasePrincipal = new BigDecimal(String.valueOf(m.get("CasePrincipal")));
                        return CasePay.compareTo(TwoAmount) == 1
                                ? (TwoAmount.compareTo(BigDecimal.ZERO) == 0 ? CasePrincipal : TwoAmount)
                                : CasePay;

//                        return CasePay.compareTo(TwoAmount) == 1
//                                ? (TwoAmount.compareTo(BigDecimal.ZERO) == 0 ? (CasePay.compareTo(CasePrincipal) == 1 ? CasePrincipal : CasePay) : TwoAmount)
//                                : CasePay;
                    }
                })
                // 使用reduce聚合函数,实现累加器
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getOwedAmount(List<Map> list) {
        return list.stream()
                .filter(item -> !"M13+".equals(item.get("OverdueInterval")))
                // 将user对象的mongey取出来map为Bigdecimal
                .map(m -> new BigDecimal(String.valueOf(m.get("CaseAmount"))))
                // 使用reduce聚合函数,实现累加器
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getRealRepayment(List<Map> list) {
        return list.stream()
                .filter(item -> !"M13+".equals(item.get("OverdueInterval")) && Double.parseDouble(item.get("CasePay").toString()) > 0)
                // 将user对象的mongey取出来map为Bigdecimal
                .map(m -> new BigDecimal(String.valueOf(m.get("CasePay"))))
                // 使用reduce聚合函数,实现累加器
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static boolean isThisTime(String LastPayDate, String CaseEndTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = fmt.parse(LastPayDate);
            date2 = fmt.parse(CaseEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String param1 = fmt.format(date1);
        String param2 = fmt.format(date2);
        if (param1.equals(param2)) {
            return true;
        }
        return false;
    }

    @Document(collection = "UserMongo")
    public static class UserMongo {

        private String account;
        private String day;
        private int count;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public UserMongo(String account) {
            Date date = new Date();
            this.account = account;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            this.day = sdf.format(date);
            Map<String, Object> map = new ConcurrentHashMap<>();
            map.put("account", account + "");
            map.put("day", sdf.format(date) + "");
            Map userMongo = MongodbUtils.findOne(Map.class, map, "UserMongo");

            if (userMongo != null) {
                this.count = Integer.parseInt(userMongo.get("count").toString()) + 1;
            } else {
                this.count = 1;
            }
        }
    }
}
