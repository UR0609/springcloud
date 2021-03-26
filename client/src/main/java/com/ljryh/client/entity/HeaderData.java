package com.ljryh.client.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ljryh
 * @date 2021/3/19 16:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HeaderData implements Serializable {

    private static final long serialVersionUID = -2427437208597155044L;

    private String label;
    private String key;

    public void setValue(String label, String key) {
        this.label = label;
        this.key = key;
    }

}
