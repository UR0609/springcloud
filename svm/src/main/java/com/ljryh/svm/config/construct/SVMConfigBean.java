package com.ljryh.svm.config.construct;

import com.ljryh.svm.entity.Configuration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ljryh
 * @date 2021/11/23 15:18
 */

@Data
@Component
@ConfigurationProperties(prefix = "svm")
public class SVMConfigBean {

    private List<Configuration> list;

}
