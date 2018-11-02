package com.shanghai.common.shiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51 redis 实现cache共享
 *           这里不使用JedisUtils，JedisUtils中定义了存储方式是json，这种方式在存储shiro
 *           cache时信息不正常
 */
public class ShiroRedisCache<K, V> implements Cache<K, V>, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(ShiroRedisCache.class);

	/**
	 * cache 过期时间
	 */
	private int cacheExpireTime;

	/**
	 * JedisPool
	 */
	private JedisPool jedisPool;

	/**
	 * 前缀
	 */
	private String prefix = "shiro:cache";

	public ShiroRedisCache(int cacheExpireTime, JedisPool jedisPool) {
		super();
		this.cacheExpireTime = cacheExpireTime / 1000;
		this.jedisPool = jedisPool;
	}

	@Override
	public void clear() throws CacheException {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix + "*";
			Set<String> set = jedis.keys(realKey);
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String keyStr = it.next();
				jedis.del(keyStr);
			}
		} finally {
			returnToPool(jedis);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public V get(K k) throws CacheException {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix + ":" + k;
			logger.debug("获取cache:{}", SerializationUtils.deserialize(jedis.get(realKey.getBytes())));
			return (V) SerializationUtils.deserialize(jedis.get(realKey.getBytes()));
		} finally {
			returnToPool(jedis);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<K> keys() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix + "*";
			Set<byte[]> bytes = jedis.keys(realKey.getBytes());
			Set<K> keys = new HashSet<>();
			if (bytes != null) {
				for (byte[] b : bytes) {
					keys.add((K) SerializationUtils.deserialize(b));
				}
			}
			return keys;
		} finally {
			returnToPool(jedis);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public V put(K k, V v) throws CacheException {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix + ":" + k;
			jedis.setex(realKey.getBytes(), cacheExpireTime, SerializationUtils.serialize(v));
			byte[] bytes = jedis.get(SerializationUtils.serialize(realKey.getBytes()));
			logger.debug("设置cache:{}", SerializationUtils.deserialize(jedis.get(realKey.getBytes())));
			return (V) SerializationUtils.deserialize(bytes);
		} finally {
			returnToPool(jedis);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public V remove(K k) throws CacheException {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix + ":" + k;
			byte[] bytes = jedis.get(realKey.getBytes());
			jedis.del(realKey.getBytes());
			return (V) SerializationUtils.deserialize(bytes);
		} finally {
			returnToPool(jedis);
		}
	}

	@Override
	public int size() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.keys(prefix).size();
		} finally {
			returnToPool(jedis);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<V> values() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix + "*";
			Set<byte[]> keys = jedis.keys(realKey.getBytes());
			List<V> lists = new ArrayList<>();
			for (byte[] k : keys) {
				byte[] bytes = jedis.get(k);
				lists.add((V) SerializationUtils.deserialize(bytes));
			}
			return lists;
		} finally {
			returnToPool(jedis);
		}
	}

	/**
	 * 关闭连接
	 * 
	 * @param jedis
	 */
	private void returnToPool(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

}