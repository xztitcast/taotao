package com.bovine.taotao.framework.service.extend;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.index.qual.NonNegative;

/**
 * 活动上下文本地缓存适配器
 * @author eden
 * @date 2023/9/23 23:50:50
 */
public class ActivityContextLocalCacheAdapter<K, V> implements ActivityContextLocalCache<K, V> {

    private Cache<K, LocalCache<V>> cache;

    public ActivityContextLocalCacheAdapter() {
        this.cache = Caffeine.newBuilder().expireAfter(new Expiry<K, LocalCache<V>>() {

            @Override
            public long expireAfterCreate(K key, LocalCache<V> value, long currentTime) {
                return value.expire;
            }

            @Override
            public long expireAfterUpdate(K key, LocalCache<V> value, long currentTime, @NonNegative long currentDuration) {
                return value.expire;
            }

            @Override
            public long expireAfterRead(K key, LocalCache<V> value, long currentTime, @NonNegative long currentDuration) {
                return value.expire;
            }
        }).initialCapacity(16).build();
    }

    @Override
    public void put(K key, V value, long expire) {
        LocalCache<V> localCache = new LocalCache<>(value, expire);
        this.cache.put(key, localCache);
    }

    @Nullable
    @Override
    public V getIfPresent(K key) {
        LocalCache<V> localCache = this.cache.getIfPresent(key);
        return localCache.t;
    }

    @AllArgsConstructor
    private static class LocalCache<T> {

        private T t;

        private long expire;
    }
}
