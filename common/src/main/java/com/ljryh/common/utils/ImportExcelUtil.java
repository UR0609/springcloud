package com.ljryh.common.utils;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ljryh
 * @date 2021/8/2 16:50
 */
public class ImportExcelUtil {

    // 自定义列英文名称
    public static String customColENName = "col";
    /**
     * 英文或中文
     */
    public static final String ENORCH = "^[a-zA-Z\\u4e00-\\u9fa5]+$";

    private static void getRowData() {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("C:\\Users\\Administrator\\Desktop\\test.xlsx"));
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            int maxRol = sheet.getRow(0).getLastCellNum();
            for (int rol = 0; rol < maxRol; rol++) {
                System.out.print(sheet.getRow(0).getCell(rol) + "  ");
            }
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getData() {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("C:\\Users\\Administrator\\Desktop\\test.xlsx"));
            List<Map<String, String>> list = new ArrayList<>();
            //读取第1个工作表
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            //获取最后一行的num，即总行数。此处从0开始
            int maxRow = sheet.getLastRowNum();
            for (int row = 1; row <= maxRow; row++) {
                //获取最后单元格num，即总单元格数 ***注意：此处从1开始计数***
                int maxRol = sheet.getRow(row).getLastCellNum();
                Map<String, String> map = new ConcurrentHashMap<>();
                for (int rol = 0; rol < maxRol; rol++) {
                    String str = sheet.getRow(row).getCell(rol).toString();
                    if (str != null && !"".equals(str) && str.contains(".") == true && str.length() - str.indexOf(".") >= 5) {
                        if (str.matches(ENORCH)) {
                            map.put(customColENName + (rol + 1), str);
                        } else {
                            map.put(customColENName + (rol + 1), str);
                        }
                    } else {
                        map.put(customColENName + (rol + 1), str);
                    }
                }
                list.add(map);
            }

            System.out.println(list);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        getData();

    }

}
