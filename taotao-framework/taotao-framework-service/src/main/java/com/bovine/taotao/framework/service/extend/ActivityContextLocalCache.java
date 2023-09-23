package com.bovine.taotao.framework.service.extend;

import jakarta.annotation.Nullable;

/**
 * 活动上下文本地缓存
 * @author eden
 * @date 2023/9/23 23:49:49
 */
public interface ActivityContextLocalCache<K, V> {

    /**
     * 添加缓存
     * @param key 缓存KEY
     * @param value 缓存值
     * @param expire 有效期
     */
    void put(K key, V value, long expire);

    /**
     * 获取缓存
     * @param key
     * @return
     */
    @Nullable
    V getIfPresent(K key);
}
