package com.ljryh.common.tools.redis;

import com.ljryh.common.tools.log.LoggerHelper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class RedisUtils {
    private LoggerHelper log = new LoggerHelper(RedisUtils.class);
    private static JedisPool readPool = null;
    private static JedisPool writePool = null;
    private static Integer index = null;

    public RedisUtils() {
        try {
            if (index == null) {
                Properties props = new Properties();
                InputStream in = MyJedisPool.class.getResourceAsStream("/redis.properties");
                props.load(in);
//                System.out.println(Integer.valueOf(props.getProperty("jedis.pool.dbIndex")));
                index = Integer.valueOf(props.getProperty("jedis.pool.dbIndex"));
            }
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
            log.error("初始redis库失败", e);
            e.printStackTrace();
        }
    }

    public RedisUtils(String resourece) {
        try {
            Properties props = new Properties();
            InputStream in = MyJedisPool.class.getResourceAsStream("/"+resourece);
            props.load(in);
            if (index == null) {
//                System.out.println("+++++++++++++++++"+Integer.valueOf(props.getProperty("jedis.pool.dbIndex")));
                index = Integer.valueOf(props.getProperty("jedis.pool.dbIndex"));
            }

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
            log.error("初始redis库失败", e);
            e.printStackTrace();
        }
    }

    public RedisUtils(String host, String port, String timeout, String password) {
        try {
            if (index == null) {
                Properties props = new Properties();
                InputStream in = MyJedisPool.class.getResourceAsStream("/redis.properties");
                props.load(in);
//                System.out.println(Integer.valueOf(props.getProperty("jedis.pool.dbIndex")));
                index = Integer.valueOf(props.getProperty("jedis.pool.dbIndex"));
            }
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
            if(host == null || port == null || "".equals(host) || "".equals(port)){
                throw new Exception("host或port 抛出异常");
            }
            if (password != null) {
                //根据配置实例化jedis池
                readPool = new JedisPool(config, host, Integer.valueOf(port), Integer.valueOf(timeout), password);
                writePool = new JedisPool(config, host, Integer.valueOf(port), Integer.valueOf(timeout), password);
            } else {
                //根据配置实例化jedis池
                readPool = new JedisPool(config, host, Integer.valueOf(port));
                writePool = new JedisPool(config, host, Integer.valueOf(port));
            }
        } catch (IOException e) {
            log.error("初始redis库失败", e);
            e.printStackTrace();
        } catch (Exception e) {
            log.error("redis 参数 error", e);
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        RedisUtils redis = new RedisUtils();

        redis.setKV("testJedisKey", "testJedisValue");

        System.out.println(redis.getKV("testJedisKey"));
        redis.setKV("testJedisKey", "123123123");
        System.out.println(redis.getKV("testJedisKey"));

        redis.delKV("testJedisKey");

        System.out.println(redis.getKV("testJedisKey"));

        System.out.println(redis.getKV("test-yh"));

        System.out.println(redis.getKV(12, "ICS_WEB_USER_SESSION_1"));

        redis.setKV("testJedisKey", "testJedisValue");

        System.out.println(redis.getKV("testJedisKey"));

        System.out.println(redis.getKV("test-yh"));
    }

    /**
     * 获取hash表中所有key
     *
     * @param name
     * @return
     */
    public static Set<String> getHashAllKey(String name) {
        Jedis jedis = null;
        try {
            jedis = readPool.getResource();
            jedis.select(index);
            return jedis.hkeys(name);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 从redis hash表中获取
     *
     * @param hashName
     * @param key
     * @return
     */
    public static String getHashKV(String hashName, String key) {
        Jedis jedis = null;
        try {
            jedis = readPool.getResource();
            jedis.select(index);
            return jedis.hget(hashName, key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 删除hash表的键值对
     *
     * @param hashName
     * @param key
     */
    public static Long delHashKV(String hashName, String key) {
        Jedis jedis = null;
        try {
            jedis = writePool.getResource();
            jedis.select(index);
            return jedis.hdel(hashName, key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 存放hash表键值对
     *
     * @param hashName
     * @param key
     * @param value
     */
    public static Long setHashKV(String hashName, String key, String value) {
        Jedis jedis = null;
        try {
            jedis = writePool.getResource();
            jedis.select(index);
            return jedis.hset(hashName, key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 删除键值对
     *
     * @param k
     * @return
     */
    public static Long delKV(String k) {
        Jedis jedis = null;
        try {
            jedis = writePool.getResource();
            jedis.select(index);
            return jedis.del(k);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 放键值对
     * 永久
     *
     * @param k
     * @param v
     */
    public static String setKV(String k, String v) {
        Jedis jedis = null;
        try {
            jedis = writePool.getResource();
            jedis.select(index);
            return jedis.set(k, v);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }


    /**
     * 放键值对
     *
     * @param k
     * @param v
     */
    public static String setKV(String k, int second, String v) {
        Jedis jedis = null;
        try {
            jedis = writePool.getResource();
            jedis.select(index);
            return jedis.setex(k, second, v);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 根据key取value
     *
     * @param k
     * @return
     */
    public static String getKV(String k) {
        Jedis jedis = null;
        try {
            jedis = readPool.getResource();
            jedis.select(index);
            return jedis.get(k);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**************************选择redis库*******************************/

    /**
     * 获取hash表中所有key
     *
     * @param name
     * @return
     */
    public static Set<String> getHashAllKey(int dbIndex, String name) {
        Jedis jedis = null;
        try {
            jedis = readPool.getResource();
            jedis.select(dbIndex);
            return jedis.hkeys(name);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 从redis hash表中获取
     *
     * @param hashName
     * @param key
     * @return
     */
    public static String getHashKV(int dbIndex, String hashName, String key) {
        Jedis jedis = null;
        try {
            jedis = readPool.getResource();
            jedis.select(dbIndex);
            return jedis.hget(hashName, key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 删除hash表的键值对
     *
     * @param hashName
     * @param key
     */
    public static Long delHashKV(int dbIndex, String hashName, String key) {
        Jedis jedis = null;
        try {
            jedis = writePool.getResource();
            jedis.select(dbIndex);
            return jedis.hdel(hashName, key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 存放hash表键值对
     *
     * @param hashName
     * @param key
     * @param value
     */
    public static Long setHashKV(int dbIndex, String hashName, String key, String value) {
        Jedis jedis = null;
        try {
            jedis = writePool.getResource();
            jedis.select(dbIndex);
            return jedis.hset(hashName, key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 删除键值对
     *
     * @param k
     * @return
     */
    public static Long delKV(int dbIndex, String k) {
        Jedis jedis = null;
        try {
            jedis = writePool.getResource();
            jedis.select(dbIndex);
            return jedis.del(k);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 放键值对
     * 永久
     *
     * @param k
     * @param v
     */
    public static String setKV(int dbIndex, String k, String v) {
        Jedis jedis = null;
        try {
            jedis = writePool.getResource();
            jedis.select(dbIndex);
            return jedis.set(k, v);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }


    /**
     * 放键值对
     *
     * @param k
     * @param v
     */
    public static String setKV(int dbIndex, String k, int second, String v) {
        Jedis jedis = null;
        try {
            jedis = writePool.getResource();
            jedis.select(dbIndex);
            return jedis.setex(k, second, v);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 根据key取value
     *
     * @param k
     * @return
     */
    public static String getKV(int dbIndex, String k) {
        Jedis jedis = null;
        try {
            jedis = readPool.getResource();
            jedis.select(dbIndex);
            return jedis.get(k);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }
}
