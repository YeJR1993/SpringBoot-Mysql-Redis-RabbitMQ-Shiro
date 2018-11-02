package com.shanghai.common.config;

import java.time.Duration;
import java.util.LinkedHashSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author YeJR
 * @version 2018年5月26日 下午5:12:29
 *          SpringBoot提供了对Redis的自动配置功能，在RedisAutoConfiguration中默认为我们配置了JedisConnectionFactory（客户端连接）、RedisTemplate以及StringRedisTemplate（数据操作模板），
 *          其中StringRedisTemplate模板只针对键值对都是字符型的数据进行操作.
 *          这里采用RedisTemplate作为数据操作模板，
 *          该模板默认采用JdkSerializationRedisSerializer的二进制数据序列化方式，
 *          采用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值，
 *          使用StringRedisSerializer来序列化和反序列化redis的key值。
 *          
 *          spring-boot-starter-data-redis jar 升级到2.0之后需要这样配置
 */
@Configuration
public class RedisCacheConfig {

	/**
	 * cache过期时间
	 */
	@Value("${data.redis.cache.expireTime}")
	private int cacheExpireTime;

	@Bean
	public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		// mybatis配置延迟加载时, json序列化异常No serializer found for class org.apache.ibatis.executor.loader....
		// 需要进行以下ObjectMapper配置
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		return jackson2JsonRedisSerializer;
	}

	/**
	 * 
	 * @param redisConnectionFactory
	 * @param resourceLoader
	 * @return
	 */
	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory,
			ResourceLoader resourceLoader) {
		// 设置缓存时间
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
				// 设置缓存时间
				.entryTtl(Duration.ofSeconds(cacheExpireTime))
				// 过滤空值
				.disableCachingNullValues()
				// 设置key的序列化方式
				.serializeKeysWith( SerializationPair.fromSerializer(new StringRedisSerializer()))
				// 设置value 序列化方式
				.serializeValuesWith(SerializationPair.fromSerializer(jackson2JsonRedisSerializer()));

		RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(config);
		builder.initialCacheNames(new LinkedHashSet<>());
		return builder.build();
	}
}
