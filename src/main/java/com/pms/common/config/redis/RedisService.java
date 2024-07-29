package com.pms.common.config.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // Store Into Ram
    public void set(String key, String value, Long timeOut) {
        redisTemplate.opsForValue().set(key,value,timeOut, TimeUnit.SECONDS);
    }

    // Get From Ram
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    // Clear Ram
    public void del(String key) {
        redisTemplate.delete(key);
    }

}
