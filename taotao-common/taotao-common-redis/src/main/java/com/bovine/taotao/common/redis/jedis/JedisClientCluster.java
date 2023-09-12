package com.bovine.taotao.common.redis.jedis;

import redis.clients.jedis.JedisCluster;

public class JedisClientCluster implements JedisClient {

	private JedisCluster jedisCluster;

	public JedisClientCluster() {

	}

	public JedisClientCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public boolean exists(String key) {
		return jedisCluster.exists(key);
	}

	@Override
	public long expire(String key, int seconds) {
		return jedisCluster.expire(key, seconds);
	}

	@Override
	public long ttl(String key) {
		return jedisCluster.ttl(key);
	}

	@Override
	public long incr(String key) {
		return jedisCluster.incr(key);
	}

	@Override
	public long hset(String key, String field, String value) {
		return jedisCluster.hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) {
		return jedisCluster.hget(key, field);
	}

	@Override
	public long hdel(String key, String... field) {
		return jedisCluster.hdel(key, field);
	}

}
