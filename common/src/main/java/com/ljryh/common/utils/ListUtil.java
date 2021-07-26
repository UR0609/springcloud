package com.ljryh.common.utils;

import com.ljryh.common.entity.CallResult;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListUtil {

    /**
     * list去重方法
     * @param keyExtractor
     * @param <T>
     * @return
     */
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static void main(String[] args) {

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
