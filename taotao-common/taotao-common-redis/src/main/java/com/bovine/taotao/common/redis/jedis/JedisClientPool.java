package com.bovine.taotao.common.redis.jedis;

import com.bovine.taotao.common.redis.processor.JedisPoolThreadLocal;
import redis.clients.jedis.Jedis;

public class JedisClientPool implements JedisClient {

	@Override
	public String set(String key, String value) {
		Jedis jedis = JedisPoolThreadLocal.getJedis();
		String result = jedis.set(key, value);
		return result;
	}

	@Override
	public String get(String key) {
		Jedis jedis = JedisPoolThreadLocal.getJedis();
		String result = jedis.get(key);
		return result;
	}

	@Override
	public boolean exists(String key) {
		Jedis jedis = JedisPoolThreadLocal.getJedis();
		boolean result = jedis.exists(key);
		return result;
	}

	@Override
	public long expire(String key, int seconds) {
		Jedis jedis = JedisPoolThreadLocal.getJedis();
		long result = jedis.expire(key, seconds);
		return result;
	}

	@Override
	public long ttl(String key) {
		Jedis jedis = JedisPoolThreadLocal.getJedis();
		long result = jedis.ttl(key);
		return result;
	}

	@Override
	public long incr(String key) {
		Jedis jedis = JedisPoolThreadLocal.getJedis();
		long result = jedis.incr(key);
		return result;
	}

	@Override
	public long hset(String key, String field, String value) {
		Jedis jedis = JedisPoolThreadLocal.getJedis();
		long result = jedis.hset(key, field, value);
		return result;
	}

	@Override
	public String hget(String key, String field) {
		Jedis jedis = JedisPoolThreadLocal.getJedis();
		String result = jedis.hget(key, field);
		return result;
	}

	@Override
	public long hdel(String key, String... field) {
		Jedis jedis = JedisPoolThreadLocal.getJedis();
		long result = jedis.hdel(key, field);
		return result;
	}

}
