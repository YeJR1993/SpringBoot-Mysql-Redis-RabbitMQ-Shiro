package com.shanghai.common.redis.rediskey;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * 定义redis的key生成的基本规则
 *
 */
public class BasePrefix implements KeyPrefix{
	
	/**
	 * 失效时间，可以通过构造函数传入
	 */
	private int expireSeconds;
	
	/**
	 * 前缀，可以通过构造函数传入
	 */
	private String prefix;
	
	
	public BasePrefix(String prefix) {//0代表永不过期
		this(0, prefix);
	}
	
	public BasePrefix( int expireSeconds, String prefix) {
		this.expireSeconds = expireSeconds;
		this.prefix = prefix;
	}
	
	@Override
	public int expireSeconds() {//默认0代表永不过期
		return expireSeconds;
	}

	/**
	 * 包名+用户给的名称
	 */
	@Override
	public String getPrefix() {
		String className = getClass().getSimpleName();
		return className+":" + prefix;
	}

}
