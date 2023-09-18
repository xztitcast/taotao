package com.bovine.taotao.common.redis.kit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * redis锁工具类
 * @author eden
 * @date 2023/8/13 15:22:22
 */
public final class RedisLockHelper {

    private static final Logger log = LoggerFactory.getLogger(RedisLockHelper.class);

    private RedissonClient redissonClient;

    public RedisLockHelper(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 加锁(默认实现)
     * @param name redis需要枷锁的KEY
     * @param action 执行函数
     * @return
     * @param <T>
     */
    public <T> T tryLock(String name, Supplier<T> action) {
        return tryLock(name, 60000L, 60000L, action);
    }

    /**
     * 加锁
     * @param name redis需要枷锁的KEY
     * @param waitTime 等待时间
     * @param expire 锁过期时间
     * @param action 执行函数
     * @return
     * @param <T>
     */
    public <T> T tryLock(String name,Long waitTime, Long expire, Supplier<T> action) {
        RLock lock = null;
        try {
            lock = this.redissonClient.getLock(name);
            boolean success = lock.tryLock(waitTime, expire, TimeUnit.MILLISECONDS);
            if(success) {
                log.info("[name:{}]加锁成功", name);
                return action.get();
            }
            log.warn("[name:{}]加锁失败", name);
            return null;
        }catch (Exception e) {
            log.error("[name:"+name+"]加锁异常", e);
            return null;
        }finally {
            if(lock!= null && lock.isLocked()) {
                lock.unlock();
                log.info("[name:{}]解锁成功", name);
            }
        }
    }
}
