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
	public Boolean exists(String key) {
		Jedis jedis = JedisPoolThreadLocal.getJedis();
		Boolean result = jedis.exists(key);
		return result;
	}

	@Override
	public Long expire(String key, int seconds) {
		Jedis jedis = JedisPoolThreadLocal.getJedis();
		Long result = jedis.expire(key, seconds);
		return result;
	}

	@Override
	public Long ttl(String key) {
		Jedis jedis = JedisPoolThreadLocal.getJedis();
		Long result = jedis.ttl(key);
		return result;
	}

	@Override
	public Long incr(String key) {
		Jedis jedis = JedisPoolThreadLocal.getJedis();
		Long result = jedis.incr(key);
		return result;
	}

	@Override
	public Long hset(String key, String field, String value) {
		Jedis jedis = JedisPoolThreadLocal.getJedis();
		Long result = jedis.hset(key, field, value);
		return result;
	}

	@Override
	public String hget(String key, String field) {
		Jedis jedis = JedisPoolThreadLocal.getJedis();
		String result = jedis.hget(key, field);
		return result;
	}

	@Override
	public Long hdel(String key, String... field) {
		Jedis jedis = JedisPoolThreadLocal.getJedis();
		Long result = jedis.hdel(key, field);
		return result;
	}

}
