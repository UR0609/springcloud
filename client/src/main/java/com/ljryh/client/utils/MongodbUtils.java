package com.ljryh.client.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 用于Mongodb处理的工具类
 *
 * @author ljryh
 * @date 2020-12-8
 */
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
     * 删除集合，根据集合名称
     *
     * @param collectionName
     */
    public static void removeAll(String collectionName) {

        mongodbUtils.mongoTemplate.dropCollection(collectionName);
    }

    /**
     * 保存数据对象，
     *
     * @param list           数据对象
     * @param collectionName 集合名
     */
    public static <T> void insert(List<T> list, String collectionName) {

        mongodbUtils.mongoTemplate.insert(list, collectionName);
    }

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
     * 指定集合 根据条件查询出所有结果集
     *
     * @param entityClass    数据对象
     * @param start          查询条件 开始范围
     * @param end            查询条件 结束范围
     * @param collectionName 集合名
     * @return
     */
    public static <T> List<T> findRange(String str, Object start, Object end, Class<T> entityClass, String collectionName) {
        if (str == null || start == null || end == null) {
            return null;
        }
        Query query = Query.query(Criteria.where(str).gt(start).lt(end));
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

        List<String> list = new CopyOnWriteArrayList<>();
        list.add(sort);

        Query query = Query.query(criteria);
//        query.with(new Sort(Sort.Direction.DESC, list));
        List<T> resultList = mongodbUtils.mongoTemplate.find(query, entityClass, collectionName);

        return resultList;
    }

    /**
     * 根据条件查询出符合的第一条数据 集合为数据对象中 @Document 注解所配置的collection
     *
     * @param entityClass 数据对象
     * @param map         查询条件 key 查询条件 value
     * @return
     */
    public static <T> T findOne(Class<T> entityClass, Map<String, Object> map) {

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
        T resultObj = mongodbUtils.mongoTemplate.findOne(query, entityClass);
        return resultObj;
    }

    public static <T> void removeById(Map<String, Object> map, String collectionName,Class<T> entityClass) {
        mongodbUtils.mongoTemplate.remove(new Query(Criteria.where("id").is(map.get("id"))),entityClass,collectionName);
    }

    /**
     * 指定集合 根据条件查询出符合的第一条数据
     *
     * @param entityClass    数据对象
     * @param map            查询条件 key 查询条件 value
     * @param collectionName 集合名
     * @return
     */
    public static <T> T findOne(Class<T> entityClass, Map<String, Object> map, String collectionName) {

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
        T resultObj = mongodbUtils.mongoTemplate.findOne(query, entityClass, collectionName);
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

    /**
     * 数据更新
     * @param entityClass
     * @param map
     * @param key
     * @param value
     * @param collectionName
     * @param <T>
     */
    public static <T> void update(Class<T> entityClass, Map<String, Object> map, String key, Object value, String collectionName) {

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
        Update update = Update.update(key, value);
        mongodbUtils.mongoTemplate.updateMulti(query, update, entityClass, collectionName);
    }

}
