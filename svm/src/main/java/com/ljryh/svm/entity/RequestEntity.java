package com.ljryh.svm.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ljryh
 * @date 2021/11/26 10:39
 */
@Data
public class RequestEntity {
    //我刷卡就是为了提额,不办
    @ApiModelProperty(value="预测内容",example="不方便接电话,一会再打吧")
    private String data;
    @ApiModelProperty(value="类型名称",example="jianhang")
    private String typeName;
}
