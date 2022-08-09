package com.ljryh.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ljryh
 * @version 1.0
 * @date 2022/5/31 11:36
 */
@NoArgsConstructor
@Data
public class ViewWflogCar {
    @JsonProperty("handletextmemo")
    private String handletextmemo;
    @JsonProperty("submittime")
    private String submittime;
    @JsonProperty("nodename")
    private String nodename;
    @JsonProperty("handlername")
    private String handlername;
}
