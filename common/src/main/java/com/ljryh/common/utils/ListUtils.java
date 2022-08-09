package com.ljryh.common.utils;

import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListUtils {

    /**
     * list去重方法
     *
     * @param keyExtractor
     * @param <T>
     * @return
     */
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static int count(String text, String sub) {
        int count = 0, start = 0;
        while ((start = text.indexOf(sub, start)) >= 0) {
            start += sub.length();
            count++;
        }
        return count;
    }

    /**
     * 读取文件最后几行 <br>
     * 相当于Linux系统中的tail命令 读取大小限制是2GB
     *
     * @param filename 文件名
     * @param charset  文件编码格式,传null默认使用defaultCharset
     * @param rows     读取行数
     * @throws IOException IOException
     */
    public static String readLastRows(String filename, Charset charset, int rows) throws IOException {
        charset = charset == null ? Charset.defaultCharset() : charset;
        byte[] lineSeparator = System.getProperty("line.separator").getBytes();
        try (RandomAccessFile rf = new RandomAccessFile(filename, "r")) {
            // 每次读取的字节数要和系统换行符大小一致
            byte[] c = new byte[lineSeparator.length];
            // 在获取到指定行数和读完文档之前,从文档末尾向前移动指针,遍历文档每一个字节
            for (long pointer = rf.length(), lineSeparatorNum = 0; pointer >= 0 && lineSeparatorNum < rows; ) {
                // 移动指针
                rf.seek(pointer--);
                // 读取数据
                int readLength = rf.read(c);
                if (readLength != -1 && Arrays.equals(lineSeparator, c)) {
                    lineSeparatorNum++;
                }
                //扫描完依然没有找到足够的行数,将指针归0
                if (pointer == -1 && lineSeparatorNum < rows) {
                    rf.seek(0);
                }
            }
            byte[] tempbytes = new byte[(int) (rf.length() - rf.getFilePointer())];
            rf.readFully(tempbytes);
            return new String(tempbytes, charset);
        }
    }

    public static String readLastRows(String filePath, int rows) throws IOException {
        Charset charset = Charset.defaultCharset();
        try (RandomAccessFile rf = new RandomAccessFile(filePath, "r")) {
            // 每次读取的字节数要和系统换行符大小一致
            byte[] c = new byte[1];
            // 在获取到指定行数和读完文档之前,从文档末尾向前移动指针,遍历文档每一个字节
            for (long pointer = rf.length(), lineSeparatorNum = 0; pointer >= 0 && lineSeparatorNum < rows; ) {
                // 移动指针
                rf.seek(pointer--);
                // 读取数据
                int readLength = rf.read(c);
                if (readLength != -1 && c[0] == 10) {
                    lineSeparatorNum++;
                }
                // 扫描完依然没有找到足够的行数,将指针归0
                if (pointer == -1 && lineSeparatorNum < rows) {
                    rf.seek(0);
                }
            }
            byte[] tempbytes = new byte[(int) (rf.length() - rf.getFilePointer())];
            rf.readFully(tempbytes);
            return new String(tempbytes, charset);
        }
    }

    public static ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                String str = tempList[i].toString();
                str = str.substring(str.lastIndexOf(File.separator) + 1);
                System.out.println("文     件：" + str);
                files.add(str);
            }
            if (tempList[i].isDirectory()) {
                String str = tempList[i].toString();
                str = str.substring(str.lastIndexOf(File.separator) + 1);
                System.out.println(str);
                System.out.println("文件夹：" + tempList[i]);
            }
        }
        return files;
    }


    @SneakyThrows
    public static void main(String[] args) {

//        String str = "http://aaa/cccc/cccc/aaaa/xiao.jpg";
//
//        // 第一种
//        int idx = str.lastIndexOf("/");
//
//        //str = str.substring(idx + 1, str.length());
//        System.out.println(str);
//
//        // 第二种
//        System.out.println(str.split("/")[str.split("/").length - 1]);
//
//        // 第三种
//        System.out.println(str.substring(str.lastIndexOf("/") + 1));
//
//        // 截取最后一个“/”前面的内容
//        System.out.println(str.substring(0, str.lastIndexOf("/")));

        getFiles("D:\\微信\\WeChat Files\\wxid_cwst9qcjekih21\\FileStorage\\File\\2021-11\\");
//        Charset charset = Charset.forName("utf8");
//        System.out.println(readLastRows("D:\\微信\\WeChat Files\\wxid_cwst9qcjekih21\\FileStorage\\File\\2021-11\\sys-error.2021-07-30.log",charset,100));
//        System.out.println(readLastRows("D:\\微信\\WeChat Files\\wxid_cwst9qcjekih21\\FileStorage\\File\\2021-11\\sys-error.2021-07-30.log",105));


//        String subject = "select a.id,b.id bid from ta a,tb b where a.id = b.di\n" +
//                "select a.id bid from tb a where 1=1 and  a.id = 1\n" +
//                "select a.id bid from tb a join ta b on a.id=b.id where 1=1 and  a.id = 1\n" +
//                "select a.id bid from tb a left join ta b on a.id=b.id where 1=1 and  a.id = 1\n" +
//                "select id bid from tb where 1=1 and  id = 1";
//        Pattern p = Pattern.compile("(?i)from\\s*([a-z]+)");
//
//        Matcher matcher = p.matcher(subject);
//        while (matcher.find()) {
//            System.out.println(matcher.group(1));
//        }
//        String text ="    ";
//        String sub =" ";
//        System.out.println(count(text,sub));
//        String str = " ";
//
//        if(str.isEmpty()){
//            System.out.println(111);
//        } else {
//            System.out.println(222);
//        }

//

//        try {
//            List<String> list1 = JacksonUtil.jsonToList("[\"44\",\"46\",\"54\",\"55\",\"179\",\"180\",\"181\",\"165\",\"169\",\"170\",\"174\",\"177\"]",String.class);
//
//            List<String> list2 = JacksonUtil.jsonToList("[\"44\",\"177\"]",String.class);
//
//            List<String> newList = list1.stream().filter(s -> !list2.contains(s)).collect(Collectors.toList());
//
//            System.out.println(JacksonUtil.objToJson(newList));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        Map<String,String> map = new ConcurrentHashMap<>();
//
//
//
//        System.out.println(map.get("CasePay"));
//
//
//
//        Double casePay = Double.parseDouble(map.get("CasePay") == null ?"0":map.get("CasePay"));
//
//        System.out.println(casePay);

//        List<TestEntity> list1 =new ArrayList();
//
//        list1.add(new TestEntity("1111",1));
//        list1.add(new TestEntity("2222",2));
//        list1.add(new TestEntity("3333",3));
//        list1.add(new TestEntity("4444",4));
//        list1.add(new TestEntity("5555",5));
//
//        List<TestEntity> list2 =new ArrayList();
//        list2.add(new TestEntity("3333",33));
//        list2.add(new TestEntity("4444",44));
//        list2.add(new TestEntity("6666",66));
//
//        Set<String> name = list2.stream().map(TestEntity::getName).collect(Collectors.toSet());
//
//        list1 = list1.stream().filter(
//                item -> name.contains(item.getName())
//        ).collect(Collectors.toList());
//
//        try {
//            System.out.println(JacksonUtil.objToJson(list1));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        List<Map<String, String>> listClass = new ArrayList<>();
//
//        CallResult callResult = new CallResult(true,1);
//
//        try {
//            List<List<Map<String, String>>> newList = JacksonUtil.jsonToList(json,listClass);
//
//            System.out.println(newList.size());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        List<CallResult> list1 = new ArrayList<>();
//        list1.add(CallResult.success(1111, "1111a", "1111a"));
//        list1.add(CallResult.success(2222, "2222a", "2222a"));
//        list1.add(CallResult.success(3333, "3333a", "3333a"));
//
//
////        List<String> list1 = new ArrayList();
////        list1.add("1111");
////        list1.add("2222");
////        list1.add("3333");
//
//        List<CallResult> list2 = new ArrayList();
//        list2.add(CallResult.success(3333, "3333a", "3333a"));
//        list2.add(CallResult.success(4444, "4444b", "4444b"));
//        list2.add(CallResult.success(5555, "5555b", "5555b"));
////        list2.add("4444");
////        list2.add("5555");
//
//        List<Map<String, Object>> list = new CopyOnWriteArrayList<>();
//        Map<String, Object> map1 = new ConcurrentHashMap<>();
//        map1.put("a1", "a1");
//        map1.put("a2", "a2");
//        list.add(map1);
//        Map<String, Object> map2 = new ConcurrentHashMap<>();
//        map2.put("b1", "b1");
//        map2.put("b2", "b2");
//        list.add(map2);
//
//        Map<String, Object> map3 = new ConcurrentHashMap<>();
//        map3.put("c1", "c1");
//        map3.put("c2", "c2");
//        list.add(map3);
//
//        List<Object> result = new ArrayList<>();
//
//        list.stream().forEach(map -> map.forEach((key, val) -> {
//                    result.add(val);
//                    System.out.println("name: " + key + " " + "value:" + val);
//                }
//                )
//        );
//
//        System.out.println(123);

//        // 差集 (list1 - list2)
//        List<String> reduce1 = list1.stream().filter(item -> !list2.contains(item)).collect(Collectors.toList());
//
//        System.out.println(reduce1);
//
//        // 差集 (list2 - list1)
//        List<String> reduce2 = list2.stream().filter(item -> !list1.contains(item)).collect(Collectors.toList());
//
//        System.out.println(reduce2);
//
//        // 并集
//        List<String> listAll = list1.parallelStream().collect(Collectors.toList());
//        List<String> listAll2 = list2.parallelStream().collect(Collectors.toList());
//        listAll.addAll(listAll2);
//
//        System.out.println(listAll);
//
//        // 去重并集
//        List<String> listAllDistinct = listAll.stream().distinct().collect(Collectors.toList());
//
//        System.out.println(listAllDistinct);

    }

}
