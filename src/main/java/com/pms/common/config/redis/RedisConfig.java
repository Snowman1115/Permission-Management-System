package com.pms.common.config.redis;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    @SuppressWarnings(value = { "unchecked", "rawTyoes" })
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);

        // Utilize StringRedisSerializer to List DescOrder or AscOrder in Redis's Key Value
        template.setKeySerializer(new StringRedisSerializer());
        template.setKeySerializer(serializer);

        // Hash Key utilize StringRedisSerializer's AscOrder
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }

}
