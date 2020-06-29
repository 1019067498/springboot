package com.example.demo.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * @Author jiaqi.qu@hand-china.com 2020/6/8 15:52
 * @Version 1.0
 * Redis配置类
 */
@Configuration
public class MyRedisTemplate {
    /**
     * 自定义redis序列化方式，否则键值以二进制存储
     */
    @Resource
    private RedisConnectionFactory factory;
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // key采用String的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // hash的key也采用String的序列化方式
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        // hash的value序列化方式采用jackson
        redisTemplate.setValueSerializer(new FastJsonRedisSerializer(Object.class));
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;

    }
}
