package com.ljryh.svm.utils;

import com.ljryh.svm.config.construct.Constant;
import com.ljryh.svm.config.construct.LibSVM;
import com.ljryh.svm.entity.Configuration;
import com.ljryh.svm.entity.ExcelData;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ljryh
 * @date 2021/11/25 17:19
 */
public class SVMExcelUtils {

    public static List<ExcelData> readExcelData(Configuration configuration, RedisUtils redisUtils){
        List<ExcelData> list = new ArrayList<>();
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(configuration.getPath()));
            XSSFSheet sheet = xssfWorkbook.getSheetAt(configuration.getSheet());
            //获取最后一行的num，即总行数。此处从0开始
            int maxRow = sheet.getLastRowNum();
            for (int row = 1; row <= maxRow; row++) {
                ExcelData excelData = new ExcelData(sheet.getRow(row).getCell(0).toString(),
                        sheet.getRow(row).getCell(1).toString());
                list.add(excelData);

                String example = sheet.getRow(row).getCell(1).toString();
                List<Term> terms = ToAnalysis.parse(example).recognition(LibSVM.filter).getTerms();
                for (Term t : terms) {
                    String name = UnicodeUtils.wordChangeUnicode(t.getName());
                    Object dataCount = redisUtils.get(configuration.getName() + Constant.SVM_DATA_COUNT + name);
                    Object data = redisUtils.get(configuration.getName() + Constant.SVM_DATA + name);
                    // 记录分词索引
                    if (data == null) {
                        int redisDataCount;
                        if (redisUtils.get(configuration.getName() + Constant.SVM_COUNT) == null) {
                            redisUtils.set(configuration.getName() + Constant.SVM_COUNT, 1);
                            redisDataCount = 0;
                        } else {
                            redisDataCount = Integer.parseInt(redisUtils.get(configuration.getName() + Constant.SVM_COUNT).toString());
                        }
                        redisUtils.set(configuration.getName() + Constant.SVM_DATA + name, redisDataCount++);
                        redisUtils.set(configuration.getName() + Constant.SVM_COUNT, redisDataCount);
                    }
                    // 记录出现的频率
                    if (dataCount == null) {
                        redisUtils.set(configuration.getName() + Constant.SVM_DATA_COUNT + name, 1);
                    } else {
                        redisUtils.set(configuration.getName() + Constant.SVM_DATA_COUNT + name, Integer.parseInt(dataCount.toString()) + 1);
                    }
                }
                // 记录词语总量
                Object obj = redisUtils.get(configuration.getName() + Constant.SVM_TOTAL_COUNT);
                if (obj == null) {
                    redisUtils.set(configuration.getName() + Constant.SVM_TOTAL_COUNT, terms.size());
                } else {
                    redisUtils.set(configuration.getName() + Constant.SVM_TOTAL_COUNT, Integer.parseInt(obj.toString()) + terms.size());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
