package com.example.di.config;

import java.util.HashMap;
import java.util.Map;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@ConditionalOnProperty("application.cache.enabled.test")
public class CacheConfig {

	@Bean
	public CacheManager getCache(RedissonClient redissonClient) {
		Map<String, CacheConfig> config = new HashMap<>();
		config.put("users", new CacheConfig());
		return new RedissonSpringCacheManager(redissonClient);
	}
	
	@Bean(destroyMethod = "shutdown")
	public RedissonClient redisson(){
		Config config = new Config();
		config.useSingleServer().setAddress("redis://127.0.0.1:6379");
		return Redisson.create(config);
		}
}
