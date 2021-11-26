package com.ljryh.svm.config.construct;

import com.ljryh.common.utils.TXTUtils;
import com.ljryh.svm.config.factory.SVMStrategyFactory;
import com.ljryh.svm.entity.Configuration;
import com.ljryh.svm.service.SVMStrategy;
import com.ljryh.svm.utils.RedisUtils;
import libsvm.svm_model;
import org.ansj.recognition.impl.StopRecognition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ljryh
 * @date 2021/11/18 9:08
 */
@Component
public class LibSVM {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private SVMConfigBean svmConfigBean;

    @Value("${stop.word.path}")
    private String stop_word_path;

    public static Map<String, svm_model> svm_maps = new ConcurrentHashMap<>();

    public static StopRecognition filter = new StopRecognition();

    @PostConstruct
    public void textInference(){
        filter.insertStopWords(TXTUtils.getStopWords(stop_word_path)); //过滤单词

        for(Configuration configuration : svmConfigBean.getList()){
            SVMStrategy strategy = SVMStrategyFactory.getInstance().get(configuration.getType());
            svm_maps.put(configuration.getName(),strategy.getModle(configuration,redisUtils));
        }

    }

}
