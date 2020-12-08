package com.ljryh.client.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MongodbUtils {

    public static MongodbUtils mongodbUtils;

    @PostConstruct
    public void init() {
        mongodbUtils = this;
        mongodbUtils.mongoTemplate = this.mongoTemplate;
    }

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 保存数据对象，集合为数据对象中@Document 注解所配置的collection
     *
     * @param obj 数据对象
     */
    public static void save(Object obj) {

        mongodbUtils.mongoTemplate.save(obj);
    }

    /**
     * 指定集合保存数据对象
     *
     * @param obj            数据对象
     * @param collectionName 集合名
     */
    public static void save(Object obj, String collectionName) {

        mongodbUtils.mongoTemplate.save(obj, collectionName);
    }

    /**
     * 根据数据对象中的id删除数据，集合为数据对象中@Document 注解所配置的collection
     *
     * @param obj 数据对象
     */
    public static void remove(Object obj) {

        mongodbUtils.mongoTemplate.remove(obj);
    }

    /**
     * 指定集合 根据数据对象中的id删除数据
     *
     * @param obj            数据对象
     * @param collectionName 集合名
     */
    public static void remove(Object obj, String collectionName) {

        mongodbUtils.mongoTemplate.remove(obj, collectionName);
    }

    /**
     * 根据key，value到指定集合删除数据
     *
     * @param key            键
     * @param value          值
     * @param collectionName 集合名
     */
    public static void removeById(String key, Object value, String collectionName) {

        Criteria criteria = Criteria.where(key).is(value);
        criteria.and(key).is(value);
        Query query = Query.query(criteria);
        mongodbUtils.mongoTemplate.remove(query, collectionName);
    }

    /**
     * 指定集合 修改数据，且仅修改找到的第一条数据
     *
     * @param accordingKey   修改条件 key
     * @param accordingValue 修改条件 value
     * @param updateKeys     修改内容 key数组
     * @param updateValues   修改内容 value数组
     * @param collectionName 集合名
     */
    public static void updateFirst(String accordingKey, Object accordingValue, String[] updateKeys, Object[] updateValues,
                                   String collectionName) {

        Criteria criteria = Criteria.where(accordingKey).is(accordingValue);
        Query query = Query.query(criteria);
        Update update = new Update();
        for (int i = 0; i < updateKeys.length; i++) {
            update.set(updateKeys[i], updateValues[i]);
        }
        mongodbUtils.mongoTemplate.updateFirst(query, update, collectionName);
    }

    /**
     * 指定集合 修改数据，且修改所找到的所有数据
     *
     * @param accordingKey   修改条件 key
     * @param accordingValue 修改条件 value
     * @param updateKeys     修改内容 key数组
     * @param updateValues   修改内容 value数组
     * @param collectionName 集合名
     */
    public static void updateMulti(String accordingKey, Object accordingValue, String[] updateKeys, Object[] updateValues,
                                   String collectionName) {

        Criteria criteria = Criteria.where(accordingKey).is(accordingValue);
        Query query = Query.query(criteria);
        Update update = new Update();
        for (int i = 0; i < updateKeys.length; i++) {
            update.set(updateKeys[i], updateValues[i]);
        }
        mongodbUtils.mongoTemplate.updateMulti(query, update, collectionName);
    }

    /**
     * 根据条件查询出所有结果集 集合为数据对象中@Document 注解所配置的collection
     *
     * @param entityClass 数据对象
     * @param map         查询条件 key 查询条件 value
     * @return
     */
    public static <T> List<T> find(Map<String, Object> map, Class<T> entityClass) {

        Criteria criteria = null;
        int count = 0;
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (count == 0)
                    criteria = Criteria.where(entry.getKey()).is(entry.getValue());
                else
                    criteria.and(entry.getKey()).is(entry.getValue());
                count++;
            }
        }

        Query query = Query.query(criteria);
        List<T> resultList = mongodbUtils.mongoTemplate.find(query, entityClass);

        return resultList;
    }

    /**
     * 指定集合 根据条件查询出所有结果集
     *
     * @param entityClass    数据对象
     * @param map            查询条件 key 查询条件 value
     * @param collectionName 集合名
     * @return
     */
    public static <T> List<T> find(Map<String, Object> map, Class<T> entityClass, String collectionName) {

        Criteria criteria = null;
        if (map != null) {
            int count = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (count == 0)
                    criteria = Criteria.where(entry.getKey()).is(entry.getValue());
                else
                    criteria.and(entry.getKey()).is(entry.getValue());
                count++;
            }
        }

        Query query = Query.query(criteria);
        List<T> resultList = mongodbUtils.mongoTemplate.find(query, entityClass, collectionName);

        return resultList;
    }

    /**
     * 指定集合 根据条件查询出所有结果集 并排倒序
     *
     * @param entityClass    数据对象
     * @param map            查询条件 key 查询条件 value
     * @param collectionName 集合名
     * @param sort           排序字段
     * @return
     */
    public static <T> List<T> find(Map<String, Object> map, Class<T> entityClass, String collectionName, String sort) {

        Criteria criteria = null;
        int count = 0;
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (count == 0)
                    criteria = Criteria.where(entry.getKey()).is(entry.getValue());
                else
                    criteria.and(entry.getKey()).is(entry.getValue());
                count++;
            }
        }

        Query query = Query.query(criteria);
        query.with(new Sort(Sort.Direction.DESC, sort));
        List<T> resultList = mongodbUtils.mongoTemplate.find(query, entityClass, collectionName);

        return resultList;
    }

    /**
     * 根据条件查询出符合的第一条数据 集合为数据对象中 @Document 注解所配置的collection
     *
     * @param obj        数据对象
     * @param findKeys   查询条件 key
     * @param findValues 查询条件 value
     * @return
     */
    public static Object findOne(Object obj, String[] findKeys, Object[] findValues) {

        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);
        Object resultObj = mongodbUtils.mongoTemplate.findOne(query, obj.getClass());
        return resultObj;
    }

    /**
     * 指定集合 根据条件查询出符合的第一条数据
     *
     * @param obj            数据对象
     * @param findKeys       查询条件 key
     * @param findValues     查询条件 value
     * @param collectionName 集合名
     * @return
     */
    public static Object findOne(Object obj, String[] findKeys, Object[] findValues, String collectionName) {

        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);
        Object resultObj = mongodbUtils.mongoTemplate.findOne(query, obj.getClass(), collectionName);
        return resultObj;
    }

    /**
     * 查询出所有结果集 集合为数据对象中 @Document 注解所配置的collection
     *
     * @param entityClass 数据对象
     * @return
     */
    public static <T> List<T> findAll(Class<T> entityClass) {

        List<T> resultList = mongodbUtils.mongoTemplate.findAll(entityClass);
        return resultList;
    }

    /**
     * 指定集合 查询出所有结果集
     *
     * @param entityClass    数据对象
     * @param collectionName 集合名
     * @return
     */
    public static <T> List<T> findAll(Class<T> entityClass, String collectionName) {

        List<T> resultList = mongodbUtils.mongoTemplate.findAll(entityClass, collectionName);
        return resultList;
    }

}
