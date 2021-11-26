package com.ljryh.svm.entity;

import lombok.Data;

/**
 * @author ljryh
 * @date 2021/11/23 15:17
 */
@Data
public class Configuration {
    private String name;
    private String path;
    private Integer type;
    private Integer sheet;
    private Integer intention;
    private Integer example;
    private Integer c;
    private Integer gamma;
}
