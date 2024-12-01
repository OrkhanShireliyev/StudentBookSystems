package com.example.springbootstudent.config;

import com.example.springbootstudent.model.Student;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Student> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String,Student> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }

//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory connectionFactory){
//        RedisCacheConfiguration redisCacheConfiguration=
//                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(12));
//
//        return RedisCacheManager.builder()
//                .cacheDefaults(redisCacheConfiguration)
//                .build();
//    }

}
