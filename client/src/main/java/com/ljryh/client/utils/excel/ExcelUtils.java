package com.ljryh.client.utils.excel;

import org.apache.poi.hssf.usermodel.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelUtils {
    public static void main(String[] args) {
        ExcelUtils transToExcel = new ExcelUtils();
        try {
            String path = System.getProperty("user.dir");
            OutputStream os = new FileOutputStream(path+"/学生表测试.xls");
            String[] headers = {
                    "大区", "初始价格", "页面价格", "唯一码", "二号位一速", "二号位一速名字",
                    "二号位二速", "二号位二速名字", "二号位三速", "二号位三速名字", "尾速", "尾速名字",
                    "一号位招财速度", "三号位招财速度", "四号位招财速度", "五号位招财速度", "六号位招财速度", "一号一速",
                    "三号三速", "四号四速", "五号五速", "六号六速", "体力", "勾玉" , "url"};
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(date);
            String[][] list = {
//                    {"白滚滚上神","1000000","20100914001000000000","http://baigungun.blog.com.cn/index",time},
//                    {"天族夜华","300000","20100914002","http://yehua.com.cn/index",time}
            };
            transToExcel.exporteExcel("学生表",headers,list,os);
            os.close();

        }catch (FileNotFoundException e){
            System.out.println("无法找到文件");
        }catch (IOException e){
            System.out.println("写入文件失败");
        }
    }

    /**
     * 导出excel文件
     *
     * @param title    表sheet的名字
     * @param headers  表头
     * @param dataList 正文单元格
     * @param out      输出流
     */
    public void exporteExcel(String title, String[] headers, Object[][] dataList, OutputStream out) {
        HSSFWorkbook workBook = new HSSFWorkbook();
        createSheet(title, headers, dataList, workBook);
        createSheet(title + "2", headers, dataList, workBook);
        try {
            workBook.write(out);
        } catch (IOException e) {
            System.out.println("写入文件失败" + e.getMessage());
        }
    }


    /**
     * 创建sheet
     *
     * @param title    sheet的名字
     * @param headers  表头
     * @param dataList 正文单元格
     */
    private void createSheet(String title, String[] headers, Object[][] dataList, HSSFWorkbook workBook) {
        HSSFSheet sheet = workBook.createSheet(title);
//        sheet.setDefaultColumnWidth(15);
        //设置表头和普通单元格的格式
        HSSFCellStyle headStyle = setHeaderStyle(workBook);
        HSSFCellStyle bodyStyle = setBodyStyle(workBook);

        createBody(dataList, sheet, bodyStyle);
        createHeader(headers, sheet, headStyle);
    }

    /**
     * 创建正文单元格
     *
     * @param dataList  数据数组
     * @param sheet     表
     * @param bodyStyle 单元格格式
     */
    private void createBody(Object[][] dataList, HSSFSheet sheet, HSSFCellStyle bodyStyle) {
        for (int a = 0; a < dataList.length; a++) {
            HSSFRow row = sheet.createRow(a + 1);
            for (int j = 0; j < dataList[a].length; j++) {
                HSSFCell cell = row.createCell(j);
                cell.setCellStyle(bodyStyle);
                HSSFRichTextString textString;
                if(dataList[a][j] != null){
                     textString = new HSSFRichTextString(dataList[a][j].toString());
                } else {
                     textString = new HSSFRichTextString("");
                }
                cell.setCellValue(textString);
            }
        }
    }

    /**
     * 创建表头
     *
     * @param headers   表头
     * @param sheet     表
     * @param headStyle 表头格式
     */
    private void createHeader(String[] headers, HSSFSheet sheet, HSSFCellStyle headStyle) {
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(headStyle);
            HSSFRichTextString textString = new HSSFRichTextString(headers[i]);
            cell.setCellValue(textString);
            sheet.autoSizeColumn((short) i);
        }
    }


    /**
     * 设置正文单元格格式
     *
     * @param workBook
     * @return
     */
    private HSSFCellStyle setBodyStyle(HSSFWorkbook workBook) {
        HSSFCellStyle style2 = workBook.createCellStyle();
//        style2.setFillForegroundColor(HSSFColor.WHITE.index);
//        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        HSSFFont font2 = workBook.createFont();
        font2.setFontName("微软雅黑");
//        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        style2.setFont(font2);
        return style2;
    }

    /**
     * 设置表头格式
     *
     * @param workBook
     * @return
     */
    private HSSFCellStyle setHeaderStyle(HSSFWorkbook workBook) {
        HSSFCellStyle style = workBook.createCellStyle();
//        style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        HSSFFont font = workBook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 12);
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        return style;
    }
}
