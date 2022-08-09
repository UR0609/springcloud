package com.ljryh.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtils {

	private static final String DEFAULT_ENCODING = "UTF-8";

	private static Logger log = LoggerFactory.getLogger(PropertiesUtils.class);

	private static PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

	private static Properties properties;

	public PropertiesUtils(String resourcesPaths) {
		try {
			properties = loadProperties(resourcesPaths);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 载入多个properties文件, 相同的属性在最后载入的文件中的值将会覆盖之前的载入.
	 * 文件路径使用Spring Resource格式, 文件编码使用UTF-8.
	 *
	 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
	 */
	private Properties loadProperties(String... resourcesPaths) throws IOException {
		Properties props = new Properties();

		for (String location : resourcesPaths) {

			log.debug("Loading properties file from:" + location);

			InputStream is = null;
			try {
				Resource resource = resourceLoader.getResource(location);
				is = resource.getInputStream();
				propertiesPersister.load(props, new InputStreamReader(is, DEFAULT_ENCODING));
			} catch (IOException ex) {
				log.info("Could not load properties from classpath:" + location + ": " + ex.getMessage());
			} finally {
				if (is != null) {
					is.close();
				}
			}
		}
		return props;
	}

	private Properties load(String path){
		Properties p = new Properties();
		try {
			InputStream in  = ClassLoader.getSystemResourceAsStream(path);
			p.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}

	private String getValue(String key) {
		try {
			if(properties == null) {
				return null;
				//properties = PropertiesUtil.loadProperties("/config/server.properties");
			}
			return new String((properties.getProperty(key)).getBytes("ISO8859_1"), "utf-8").trim();
		} catch (Exception e) { }
		return "";
	}

	/**
	 * 获取整型配置项.
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public Integer getInt(String key, Integer defaultVal) {
		try {
			String valStr = getValue(key);
			return Integer.parseInt(valStr.trim());
		} catch (Exception e) {	}
		return defaultVal;
	}

	/**
	 * 获取布尔配置.
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public boolean getBoolean(String key, boolean defaultVal) {
		try {
			String valStr = getValue(key);
			return Boolean.parseBoolean(valStr.trim());
		} catch (Exception e) {	}
		return defaultVal;
	}

	/**
	 * 获取字符型配置项.
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public String getString(String key, String defaultVal) {
		try {
			return getValue(key).trim();
		} catch (Exception e) {	}
		return defaultVal;
	}

	public static void main(String[] args) {
		PropertiesUtils propertiesUtil = new PropertiesUtils("/redis.properties");
		System.out.println("jedis.pool.maxActive:"+propertiesUtil.getString("jedis.pool.maxActive", "0"));
	}

}
