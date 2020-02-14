package com.ljryh.common.tools.redis;

import com.ljryh.common.tools.log.LoggerHelper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyJedisPool {
    private static LoggerHelper log = new LoggerHelper(MyJedisPool.class);

    private static JedisPool readPool = null;
    private static JedisPool writePool = null;

    //静态代码初始化池配置
    static {
        try {
            Properties props = new Properties();
            InputStream in = MyJedisPool.class.getResourceAsStream("/redis.properties");
            props.load(in);

            //创建jedis池配置实例
            JedisPoolConfig config = new JedisPoolConfig();

            //设置池配置项值
            //设置最大连接数
            config.setMaxTotal(Integer.valueOf(props.getProperty("jedis.pool.maxActive")));
            //设置最大空闲数
            config.setMaxIdle(Integer.valueOf(props.getProperty("jedis.pool.maxIdle")));
            //设置超时时间
            config.setMaxWaitMillis(Long.valueOf(props.getProperty("jedis.pool.maxWait")));
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(Boolean.valueOf(props.getProperty("jedis.pool.testOnBorrow")));
            config.setTestOnReturn(Boolean.valueOf(props.getProperty("jedis.pool.testOnReturn")));

            if (String.valueOf(props.getProperty("jedis.pool.password")) != null) {
                //根据配置实例化jedis池
                readPool = new JedisPool(config, String.valueOf(props.getProperty("jedis.pool.host")),
                        Integer.valueOf(props.getProperty("jedis.pool.port")),
                        Integer.valueOf(props.getProperty("jedis.pool.timeout")),
                        String.valueOf(props.getProperty("jedis.pool.password")));
                writePool = new JedisPool(config, String.valueOf(props.getProperty("jedis.pool.host")),
                        Integer.valueOf(props.getProperty("jedis.pool.port")),
                        Integer.valueOf(props.getProperty("jedis.pool.timeout")),
                        String.valueOf(props.getProperty("jedis.pool.password")));
            } else {
                //根据配置实例化jedis池
                readPool = new JedisPool(config, String.valueOf(props.getProperty("jedis.pool.host")),
                        Integer.valueOf(props.getProperty("jedis.pool.port")));
                writePool = new JedisPool(config, String.valueOf(props.getProperty("jedis.pool.host")),
                        Integer.valueOf(props.getProperty("jedis.pool.port")));
            }
        } catch (IOException e) {
            log.error("redis连接池异常", e);
        }
    }


    /**
     * 获得jedis对象
     */
    public static Jedis getReadJedisObject() {
        return readPool.getResource();
    }

    /**
     * 获得jedis对象
     */
    public static Jedis getWriteJedisObject() {
        return writePool.getResource();
    }

    /**
     * 归还jedis对象
     */
    public static void returnJedisOjbect(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
