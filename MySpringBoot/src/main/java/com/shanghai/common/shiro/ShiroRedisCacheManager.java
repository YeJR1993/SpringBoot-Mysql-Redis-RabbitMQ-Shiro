package com.shanghai.common.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import redis.clients.jedis.JedisPool;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * cache管理类
 */
public class ShiroRedisCacheManager implements CacheManager {


	/**
	 * cache 过期时间
	 */
	private int cacheExpireTime;

	/**
	 * JedisPool
	 */
	private JedisPool jedisPool;
	
	public ShiroRedisCacheManager(int cacheExpireTime, JedisPool jedisPool) {
		super();
		this.cacheExpireTime = cacheExpireTime;
		this.jedisPool = jedisPool;
	}

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return new ShiroRedisCache<>(cacheExpireTime, jedisPool);
	}

}
