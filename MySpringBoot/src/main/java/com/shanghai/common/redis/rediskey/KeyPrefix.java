package com.shanghai.common.redis.rediskey;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * 为redis的key添加前缀，保证key不重复
 */
public interface KeyPrefix {
	
	/**
	 * 失效时间
	 * @return
	 */
	public int expireSeconds();
	
	/**
	 * 获取前缀
	 * @return
	 */
	public String getPrefix();
	
}
