package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author cdx
 * @date 2018/9/6
 */
@Service
public class RedisFileService {
    /**
     * 信息存入redis
     */

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @param nameKey   key
     * @param nameValue value
     */
    public void saveFilePathToRedis(String nameKey, String nameValue) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(nameKey, nameValue, 10, TimeUnit.MINUTES);
    }

    public void saveFileTimeToRedis(String nameKey, Long nameValue) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(nameKey, nameValue, 10, TimeUnit.MINUTES);
    }

    /**
     * @param nameKey key
     * @return
     */
    public String getFilePathFromRedis(String nameKey) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String verifyName = (String) valueOperations.get(nameKey);
        return verifyName;
    }
    public Long getFileTimeFromRedis(String nameKey) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Long verifyName = (Long) valueOperations.get(nameKey);
        return verifyName;
    }
}