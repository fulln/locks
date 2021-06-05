package me.fulln.lock.service;

import me.fulln.lock.domain.LockDomain;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author fulln
 * @version 0.0.1
 * @program locks
 * @description
 * @date 2021/6/5 2:07
 **/
@Component
public class RedisLockService implements LockDomain {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 获取锁
     *
     * @param key
     * @param version
     * @return 是否成功
     */
    @Override
    public String tryLock(String key, String version) {
        Boolean locks = redisTemplate.opsForValue().setIfAbsent(key, "1", 1, TimeUnit.MINUTES);
        if (BooleanUtils.toBooleanDefaultIfNull(locks, false)) {
            return key;
        } else {
            return "";
        }
    }

    /**
     * 释放锁
     *
     * @param key
     * @param version
     * @return 是否成功
     */
    @Override
    public boolean releaseLock(String key, String version) {
        return BooleanUtils.toBooleanDefaultIfNull(redisTemplate.delete(key), false);
    }
}
