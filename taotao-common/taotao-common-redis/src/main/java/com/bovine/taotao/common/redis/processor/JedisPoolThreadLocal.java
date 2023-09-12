package com.bovine.taotao.common.redis.processor;

import redis.clients.jedis.Jedis;

/**
 * @author eden
 * @date 2023/8/13 17:39:39
 */
public abstract class JedisPoolThreadLocal {

    private static final ThreadLocal<Jedis> JEDIS_THREAD_LOCAL = new ThreadLocal<>();

    public static void setJedis(Jedis jedis) {
        JEDIS_THREAD_LOCAL.set(jedis);
    }

    public static Jedis getJedis() {
        return JEDIS_THREAD_LOCAL.get();
    }

    public static void remove() {
        JEDIS_THREAD_LOCAL.remove();
    }
}
