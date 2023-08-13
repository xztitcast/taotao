package com.bovine.taotao.common.redis.jedis;

public interface JedisClient {

	/**
	 * redis string 类型set
	 * @param key
	 * @param value
	 * @return
	 */
	String set(String key, String value);

	/**
	 * redis string 类型get
	 * @param key
	 * @return
	 */
	String get(String key);

	/**
	 * 判断key是否存在
	 * @param key
	 * @return
	 */
	Boolean exists(String key);

	/**
	 * 设置过期时间
	 * @param key
	 * @param seconds
	 * @return
	 */
	Long expire(String key, int seconds);

	/**
	 * 查看有效期
	 * @param key
	 * @return
	 */
	Long ttl(String key);

	/**
	 * 自增
	 * @param key
	 * @return
	 */
	Long incr(String key);

	/**
	 * redis hash类型 hset
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	Long hset(String key, String field, String value);

	/**
	 * redis hash类型hget
	 * @param key
	 * @param field
	 * @return
	 */
	String hget(String key, String field);

	/**
	 * redis hash类型删除
	 * @param key
	 * @param field
	 * @return
	 */
	Long hdel(String key, String... field);
}
