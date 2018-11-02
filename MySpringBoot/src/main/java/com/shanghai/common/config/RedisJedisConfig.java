package com.shanghai.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * 在容器初始化时初始化jedis连接池
 */
@Configuration
public class RedisJedisConfig {

	/**
	 * host
	 */
	@Value("${spring.redis.host}")
	private String host;
	
	/**
	 * 端口
	 */
	@Value("${spring.redis.port}")
	private int port;
	
	/**
	 * 密码
	 */
	@Value("${spring.redis.password}")
	private String password;
	
	/**
	 * 超时
	 */
	@Value("${spring.redis.timeout}")
	private int timeout;
	
	
	
	/**
	 * 连接池最大连接数
	 */
	@Value("${spring.redis.jedis.pool.max-active}")
	private int poolMaxActive;
	
	/**
	 * 连接池最大阻塞等待时间
	 */
	@Value("${spring.redis.jedis.pool.max-wait}")
	private int poolMaxWait;
	
	/**
	 * 连接池中的最大空闲连接
	 */
	@Value("${spring.redis.jedis.pool.max-idle}")
	private int poolMaxIdle;
	
	/**
	 * 连接池中的最小空闲连接
	 */
	@Value("${spring.redis.jedis.pool.min-idle}")
	private int poolMinIdle;
	
	@Bean
	public JedisPool jedisPoolFactory() {
		
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(poolMaxActive);
		poolConfig.setMaxWaitMillis(poolMaxWait);
		poolConfig.setMaxIdle(poolMaxIdle);
		poolConfig.setMinIdle(poolMinIdle);
		
		JedisPool jedisPool = new JedisPool(poolConfig, host, port, timeout, password, 0);
		return jedisPool;
	}
	
}
