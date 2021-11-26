package com.ljryh.svm.entity;

import lombok.Data;

/**
 * @author ljryh
 * @date 2021/11/16 9:45
 */
@Data
public class ExcelData {

    private String intention;   // 意图
    private String example;     // 示例

    public ExcelData(String intention, String example) {
        this.intention = intention;
        this.example = example;
    }

}
