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
	boolean exists(String key);

	/**
	 * 设置过期时间
	 * @param key
	 * @param seconds
	 * @return
	 */
	long expire(String key, int seconds);

	/**
	 * 查看有效期
	 * @param key
	 * @return
	 */
	long ttl(String key);

	/**
	 * 自增
	 * @param key
	 * @return
	 */
	long incr(String key);

	/**
	 * redis hash类型 hset
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	long hset(String key, String field, String value);

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
	long hdel(String key, String... field);
}
