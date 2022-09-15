package com.ljryh.client.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ljryh
 * @version 1.0
 * @date 2022/9/2 11:13
 */
@Data
public class Commodity implements Serializable {
    private static final long serialVersionUID = -8594285780691470325L;
    private String id;
    private String name;
    private String price;
    private String type;
    private String status;
    private String remarks;
    private String url;
}
