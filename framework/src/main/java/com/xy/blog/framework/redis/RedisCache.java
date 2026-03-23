package com.xy.blog.framework.redis;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Redis 缓存操作工具。
 * 封装项目中常用的字符串对象、列表、集合等基础 Redis 读写能力。
 */
@Component
@RequiredArgsConstructor
public class RedisCache {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 缓存普通对象，不设置过期时间。
     */
    public <T> void setCacheObject(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存普通对象，并指定过期时间。
     */
    public <T> void setCacheObject(String key, T value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @SuppressWarnings("unchecked")
    /**
     * 获取普通对象缓存。
     */
    public <T> T getCacheObject(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除单个缓存键。
     */
    public boolean deleteObject(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 批量删除缓存键。
     */
    public long deleteObject(Collection<String> keys) {
        Long count = redisTemplate.delete(keys);
        return count == null ? 0L : count;
    }

    /**
     * 设置缓存过期时间。
     */
    public boolean expire(String key, long timeout, TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
    }

    /**
     * 获取缓存剩余过期时间，单位为秒。
     */
    public long getExpire(String key) {
        Long expire = redisTemplate.getExpire(key);
        return expire == null ? 0L : expire;
    }

    /**
     * 判断缓存键是否存在。
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 对数值缓存执行自增 1。
     */
    public long increment(String key) {
        Long value = redisTemplate.opsForValue().increment(key);
        return value == null ? 0L : value;
    }

    /**
     * 对数值缓存执行指定步长自增。
     */
    public long increment(String key, long delta) {
        Long value = redisTemplate.opsForValue().increment(key, delta);
        return value == null ? 0L : value;
    }

    /**
     * 覆盖写入列表缓存。
     */
    public <T> void setCacheList(String key, List<T> dataList) {
        redisTemplate.delete(key);
        if (!dataList.isEmpty()) {
            redisTemplate.opsForList().rightPushAll(key, dataList.toArray());
        }
    }

    @SuppressWarnings("unchecked")
    /**
     * 获取列表缓存。
     */
    public <T> List<T> getCacheList(String key) {
        return (List<T>) (List<?>) redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 覆盖写入集合缓存。
     */
    public <T> void setCacheSet(String key, Set<T> dataSet) {
        redisTemplate.delete(key);
        if (!dataSet.isEmpty()) {
            redisTemplate.opsForSet().add(key, dataSet.toArray());
        }
    }

    @SuppressWarnings("unchecked")
    /**
     * 获取集合缓存。
     */
    public <T> Set<T> getCacheSet(String key) {
        return (Set<T>) (Set<?>) redisTemplate.opsForSet().members(key);
    }
}
