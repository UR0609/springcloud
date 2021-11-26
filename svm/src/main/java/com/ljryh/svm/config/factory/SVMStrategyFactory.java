package com.ljryh.svm.config.factory;

import com.ljryh.svm.service.SVMStrategy;
import com.ljryh.svm.service.impl.ExcelStrategy;
import com.ljryh.svm.service.impl.TxtStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ljryh
 * @date 2021/11/23 16:19
 */
public class SVMStrategyFactory {

    private Map<Integer, SVMStrategy> map;

    public SVMStrategyFactory() {
        List<SVMStrategy> strategies = new ArrayList<>();

        strategies.add(new ExcelStrategy());
        strategies.add(new TxtStrategy());

        map = strategies.stream().collect(Collectors.toMap(SVMStrategy::getType, strategy -> strategy));
    }

    public static class Holder {
        public static SVMStrategyFactory instance = new SVMStrategyFactory();
    }

    public static SVMStrategyFactory getInstance() {
        return Holder.instance;
    }

    public SVMStrategy get(Integer type) {
        return map.get(type);
    }

}
