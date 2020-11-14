package com.ljryh.common.utils;

import com.ljryh.common.entity.CallResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListUtil {


    public static void main(String[] args) {

        List<CallResult> list1 = new ArrayList<>();
        list1.add(CallResult.success(1111,"1111a","1111a"));
        list1.add(CallResult.success(2222,"2222a","2222a"));
        list1.add(CallResult.success(3333,"3333a","3333a"));


//        List<String> list1 = new ArrayList();
//        list1.add("1111");
//        list1.add("2222");
//        list1.add("3333");

        List<CallResult> list2 = new ArrayList();
        list2.add(CallResult.success(3333,"3333a","3333a"));
        list2.add(CallResult.success(4444,"4444b","4444b"));
        list2.add(CallResult.success(5555,"5555b","5555b"));
//        list2.add("4444");
//        list2.add("5555");

        // 交集
        List<CallResult> intersection = list1.stream().filter(item -> list2.contains(item)).collect(Collectors.toList());

        System.out.println(intersection);


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
