package com.ljryh.common.leetcode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

    /**
     * 利用正则表达式判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static int myAtoi(String s) {
        char[] charAry = s.toCharArray();
        String data = "";
        String symbol = "";
        boolean judge = true;
        for (char c : charAry) {
            if (Character.isSpaceChar(c)) {
                data += " ";
                judge = false;
                continue;
            }
            if ((c == '+' || c == '-') && "".equals(symbol) && judge) {
                if (!judge && "".equals(data)) {
                    break;
                }
                symbol = c + "";
                continue;
            }
            if (Character.isDigit(c)) {
                judge = false;
                data += c;
            } else {
                break;
            }
        }
        symbol = symbol.equals("+") ? "" : symbol;
        data = data.replace(" ", "");
        data = data.equals("") ? "0" : data;
        if (data.equals("0")) {
            return 0;
        }
        long l = Long.parseLong(((symbol + data).equals("") ? "0" : (symbol + data)));
        l = l > 0 ? (Math.min(l, 2147483647L)) : (Math.max(l, -2147483648L));
        return Math.toIntExact(l);
    }

    public static void main(String[] args) {

        System.out.println(isNumeric("2a341"));
        /**
         * 一、系统层面
         * 1、调整同时打开文件数量
         * ulimit -n 20480
         * 2、TCP最大连接数（somaxconn）
         * echo 10000 > /proc/sys/net/core/somaxconn
         * 3、TCP连接立即回收、回用（recycle、reuse）
         * echo 1 > /proc/sys/net/ipv4/tcp_tw_reuse
         * echo 1 > /proc/sys/net/ipv4/tcp_tw_recycle
         * 4、不做TCP洪水抵御
         * echo 0 > /proc/sys/net/ipv4/tcp_syncookies
         * 也可以直接使用优化后的配置，在/etc/sysctl.conf中加入：
         * net.core.somaxconn = 20480
         * net.core.rmem_default = 262144
         * net.core.wmem_default = 262144
         * net.core.rmem_max = 16777216
         * net.core.wmem_max = 16777216
         * net.ipv4.tcp_rmem = 4096 4096 16777216
         * net.ipv4.tcp_wmem = 4096 4096 16777216
         * net.ipv4.tcp_mem = 786432 2097152 3145728
         * net.ipv4.tcp_max_syn_backlog = 16384
         * net.core.netdev_max_backlog = 20000
         * net.ipv4.tcp_fin_timeout = 15
         * net.ipv4.tcp_max_syn_backlog = 16384
         * net.ipv4.tcp_tw_reuse = 1
         * net.ipv4.tcp_tw_recycle = 1
         * net.ipv4.tcp_max_orphans = 131072
         * net.ipv4.tcp_syncookies = 0
         * 使用：sysctl -p 生效
         * sysctl -p
         */
//        String stDateWe = "";
////        String date = "2021-12-31";
//        String date = "2022-01-01";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date d = sdf.parse(date);
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(d);
//            cal.setFirstDayOfWeek(Calendar.MONDAY);  //设置一周的第一天是周一
//            cal.get(Calendar.WEEK_OF_YEAR);
//
//            int year = cal.get(Calendar.YEAR);
//            int week = cal.get(Calendar.WEEK_OF_YEAR);
//
//            cal.add(Calendar.DAY_OF_MONTH, -7);  //上一周
//
//            //判断是否同一年，并且本周周数小于上周周数，则在上周周数的基础上加一
//            if (year == cal.get(Calendar.YEAR) && week < cal.get(Calendar.WEEK_OF_YEAR)) {
//                week = cal.get(Calendar.WEEK_OF_YEAR) + 1;
//            }
//
//            stDateWe = year + "W" + (week < 10 ? "0" + week : week);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println(stDateWe);

//        System.out.println(myAtoi("   +0 123"));
    }

}
