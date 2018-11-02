package com.shanghai.common.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.shanghai.common.redis.rediskey.KeyPrefix;
import com.shanghai.common.utils.SpringContextHolder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * 这里提供了jedis工具类，用于redis的增删改查
 * 当然也可以使用spring boot 提供的RedisTemplate工具类
 */
public class JedisUtils {

	/**
	 * jedisPool工具类
	 */
	private static JedisPool jedisPool = SpringContextHolder.getBean(JedisPool.class);
	

	/**
	 * 获取当个对象
	 */
	public static <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix.getPrefix() + ":" + key;
			String str = jedis.get(realKey);
			T t = stringToBean(str, clazz);
			return t;
		} finally {
			returnToPool(jedis);
		}
	}

	/**
	 * 设置对象
	 */
	public static <T> boolean set(KeyPrefix prefix, String key, T value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String str = beanToString(value);
			if (str == null || str.length() <= 0) {
				return false;
			}
			// 生成真正的key
			String realKey = prefix.getPrefix() + ":" + key;
			int seconds = prefix.expireSeconds();
			if (seconds <= 0) {
				jedis.set(realKey, str);
			} else {
				jedis.setex(realKey, seconds, str);
			}
			return true;
		} finally {
			returnToPool(jedis);
		}
	}
	
	/**
	 * 模糊查询相应的key的集合
	 * @param prefix
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <T> Set<T> keys(KeyPrefix prefix, Class<T> clazz) {
		Jedis jedis = null;
		Set<T> result = new HashSet<T>();
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix.getPrefix() + "*";
			Set<String> keys = jedis.keys(realKey);
			for (String str : keys) {
				T t = stringToBean(str, clazz);
				result.add(t);
			}
			return result;
		} finally {
			returnToPool(jedis);
		}
	}
	
	/**
	 * 获取value集合
	 * @param prefix
	 * @param clazz
	 * @return
	 */
	public static <T> Collection<T> values(KeyPrefix prefix, Class<T> clazz) {
		List<T> lists = new ArrayList<T>();
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix.getPrefix() + "*";
			Set<String> keys = jedis.keys(realKey);
			for (String key : keys) {
				String val = jedis.get(key);
				T t = stringToBean(val, clazz);
				lists.add(t);
			}
			return lists;
		} finally {
			returnToPool(jedis);
		}
	}
	
	/**
	 * 判断key是否存在
	 */
	public static <T> boolean exists(KeyPrefix prefix, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix.getPrefix() + ":" + key;
			return jedis.exists(realKey);
		} finally {
			returnToPool(jedis);
		}
	}

	/**
	 * 删除
	 */
	public static boolean delete(KeyPrefix prefix, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix.getPrefix() + ":" + key;
			long ret = jedis.del(realKey);
			return ret > 0;
		} finally {
			returnToPool(jedis);
		}
	}

	/**
	 * 给对应的key的值增加1，错误返回-1
	 */
	public static <T> Long incr(KeyPrefix prefix, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix.getPrefix() + ":" + key;
			return jedis.incr(realKey);
		} finally {
			returnToPool(jedis);
		}
	}

	/**
	 * 给对应的key的值减少1，错误返回-1
	 */
	public static <T> Long decr(KeyPrefix prefix, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix.getPrefix() + ":" + key;
			return jedis.decr(realKey);
		} finally {
			returnToPool(jedis);
		}
	}

	/**
	 * 删除
	 * @param prefix
	 * @return
	 */
	public static boolean delete(KeyPrefix prefix) {
		if (prefix == null) {
			return false;
		}
		List<String> keys = scanKeys(prefix.getPrefix());
		if (keys == null || keys.size() <= 0) {
			return true;
		}
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(keys.toArray(new String[0]));
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			returnToPool(jedis);
		}
	}

	public static List<String> scanKeys(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			List<String> keys = new ArrayList<String>();
			String cursor = "0";
			ScanParams sp = new ScanParams();
			sp.match("*" + key + "*");
			sp.count(100);
			do {
				ScanResult<String> ret = jedis.scan(cursor, sp);
				List<String> result = ret.getResult();
				if (result != null && result.size() > 0) {
					keys.addAll(result);
				}
				// 再处理cursor
				cursor = ret.getStringCursor();
			} while (!"0".equals(cursor));
			return keys;
		} finally {
			returnToPool(jedis);
		}
	}

	public static <T> String beanToString(T value) {
		if (value == null) {
			return null;
		}
		Class<?> clazz = value.getClass();
		if (clazz == int.class || clazz == Integer.class) {
			return "" + value;
		} else if (clazz == String.class) {
			return (String) value;
		} else if (clazz == long.class || clazz == Long.class) {
			return "" + value;
		} else {
			return JSON.toJSONString(value);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T stringToBean(String str, Class<T> clazz) {
		if (str == null || str.length() <= 0 || clazz == null) {
			return null;
		}
		if (clazz == int.class || clazz == Integer.class) {
			return (T) Integer.valueOf(str);
		} else if (clazz == String.class) {
			return (T) str;
		} else if (clazz == long.class || clazz == Long.class) {
			return (T) Long.valueOf(str);
		} else {
			return JSON.toJavaObject(JSON.parseObject(str), clazz);
		}
	}

	private static void returnToPool(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}
	

}
