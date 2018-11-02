package com.shanghai.common.shiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51 redis 实现session共享
 *           这里不使用RedisService，RedisService中定义了存储方式是json，这种方式在存储shiro
 *           session时信息不正常
 */
@Component
public class ShiroRedisSessionDao extends EnterpriseCacheSessionDAO {

	private static Logger logger = LoggerFactory.getLogger(ShiroRedisSessionDao.class);

	/**
	 * session 过期时间
	 */
	private int sessionExpireTime;

	/**
	 * JedisPool
	 */
	private JedisPool jedisPool;

	/**
	 * 前缀
	 */
	private String prefix = "shiro:session";

	/**
	 * 不使用注入的方式，因为这里注入失败
	 * 
	 * @param sessionExpireTime
	 * @param redisService
	 */
	public ShiroRedisSessionDao(int sessionExpireTime, JedisPool jedisPool) {
		super();
		this.sessionExpireTime = sessionExpireTime / 1000;
		this.jedisPool = jedisPool;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = super.doCreate(session);
		logger.debug("创建session:{}", session.getId());
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix + ":" + session.getId();
			jedis.setex(realKey.getBytes(), sessionExpireTime, SerializationUtils.serialize(session));
		} finally {
			returnToPool(jedis);
		}
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		logger.debug("获取session:{}", sessionId);
		// 先从缓存中获取session，如果没有再去数据库中获取
		Session session = super.doReadSession(sessionId);
		if (session == null) {
			Jedis jedis = null;
			try {
				jedis = jedisPool.getResource();
				// 生成真正的key
				String realKey = prefix + ":" + sessionId;
				byte b[] = jedis.get(realKey.getBytes());
				session = (Session) SerializationUtils.deserialize(b);
			} finally {
				returnToPool(jedis);
			}
		}
		return session;
	}

	@Override
	protected void doUpdate(Session session) {
		super.doUpdate(session);
		logger.debug("更新session:{}", session.getId());
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix + ":" + session.getId();
			jedis.setex(realKey.getBytes(), sessionExpireTime, SerializationUtils.serialize(session));
		} finally {
			returnToPool(jedis);
		}
	}

	@Override
	protected void doDelete(Session session) {
		logger.debug("删除session:{}", session.getId());
		super.doDelete(session);
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix + ":" + session.getId();
			jedis.del(realKey.getBytes());
		} finally {
			returnToPool(jedis);
		}
	}

	@Override
	public Collection<Session> getActiveSessions() {
		List<Session> sessions = new ArrayList<Session>();
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix + "*";
			Set<byte[]> keys = jedis.keys(realKey.getBytes());
			for (byte[] k : keys) {
				byte[] bytes = jedis.get(k);
				Session session = (Session) SerializationUtils.deserialize(bytes);
				//将登入过的session放入集合
				if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) != null) {
					sessions.add(session);
				}
			}
		} finally {
			returnToPool(jedis);
		}

		return sessions;
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
