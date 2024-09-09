package com.ljryh.common.utils;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ljryh
 * @version 1.0
 * @date 2024/9/9 13:37
 */
public class HolidayUtlis {

    /*国务院办公厅关于2024年部分节假日安排的通知*/

    static List<String> holiday = new ArrayList<>();
    static List<String> extraWorkDay = new ArrayList<>();

    public static int isWorkingDay(long time) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.of("+8"));
        System.out.println(dateTime);
        String formatTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(formatTime);
        initHoliday();
        initExtraWorkDay();
        //是否加班日
        if (extraWorkDay.contains(formatTime)) {
            return 0;
        }
        //是否节假日
        if (holiday.contains(formatTime)) {
            return 2;
        }
        //如果是1-5表示周一到周五  是工作日
        DayOfWeek week = dateTime.getDayOfWeek();
        if (week == DayOfWeek.SATURDAY || week == DayOfWeek.SUNDAY) {
            return 1;
        }
        return 0;

    }

    public static void main(String[] args) {
        int workingDay = isWorkingDay(System.currentTimeMillis());
        if (workingDay == 0) {
            System.out.println("工作日，加油，打工人");
        } else {
            System.out.println("开开心心过节，高高兴兴干饭！！！");
        }
    }

    /**
     * 初始化节假日
     */
    public static void initHoliday() {
        holiday.add("2024-01-01");
        holiday.add("2024-01-02");
        holiday.add("2024-01-03");
        holiday.add("2024-02-10");
        holiday.add("2024-02-11");
        holiday.add("2024-02-12");
        holiday.add("2024-02-13");
        holiday.add("2024-02-14");
        holiday.add("2024-02-15");
        holiday.add("2024-02-16");
        holiday.add("2024-02-17");
        holiday.add("2024-04-04");
        holiday.add("2024-04-05");
        holiday.add("2024-04-06");
        holiday.add("2024-05-01");
        holiday.add("2024-05-02");
        holiday.add("2024-05-03");
        holiday.add("2024-05-04");
        holiday.add("2024-05-05");
        holiday.add("2024-06-10");
        holiday.add("2024-06-11");
        holiday.add("2024-06-12");
        holiday.add("2024-09-15");
        holiday.add("2024-09-16");
        holiday.add("2024-09-17");
        holiday.add("2024-10-01");
        holiday.add("2024-10-02");
        holiday.add("2024-10-03");
        holiday.add("2024-10-04");
        holiday.add("2024-10-05");
        holiday.add("2024-10-06");
        holiday.add("2024-10-07");
    }

    /**
     * 初始化额外加班日
     */
    public static void initExtraWorkDay() {
        extraWorkDay.add("2024-02-04");
        extraWorkDay.add("2024-02-18");
        extraWorkDay.add("2024-04-07");
        extraWorkDay.add("2024-04-28");
        extraWorkDay.add("2024-05-11");
        extraWorkDay.add("2024-09-14");
        extraWorkDay.add("2024-09-29");
        extraWorkDay.add("2024-10-12");
    }
}
