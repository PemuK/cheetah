package com.ujnbox.cheetah.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 设置键的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 设置值的序列化方式
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        // 设置哈希键的序列化方式
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        // 设置哈希值的序列化方式
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}