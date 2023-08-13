package com.bovine.taotao.common.redis;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bovine.taotao.common.redis.jedis.JedisClient;
import com.bovine.taotao.common.redis.jedis.JedisClientCluster;
import com.bovine.taotao.common.redis.jedis.JedisClientPool;
import com.bovine.taotao.common.redis.kit.RedisLockHelper;
import com.bovine.taotao.common.redis.processor.JedisPoolThreadLocal;
import com.bovine.taotao.common.redis.processor.RedisDbIndexThreadLocal;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Sentinel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import com.bovine.taotao.common.redis.processor.RedisDbIndexSwitchProcessor;
import redis.clients.jedis.*;

/**
 * 数据字典redis配置
 * 该配置只允许读取数据字典数据
 * 不允许使用该dictRedisTemplate做任何的其他操作
 * 其他操作请注入Spring自带的redisTemplate
 *
 * redis配置注意点升级Spring boot3.0后redis配置文件已更改
 * Spring boot3.x: spring.data.redis.xxx
 * Spring boot2.x: spring.redis.xxx
 * @author eden
 * @date 2022年11月20日 下午9:48:23
 */
@Configuration
public class RedisTemplateAutoConfiguration {
	
	private static final String REDIS_PROTOCOL_PREFIX = "redis://";
	
	private static final String REDISS_PROTOCOL_PREFIX = "rediss://";
	
	private final RedisProperties redisProperties;
	
	public RedisTemplateAutoConfiguration(RedisProperties redisProperties) {
		this.redisProperties = redisProperties;
	}
	
	/*@Primary
	@Bean(name = "stringRedisTemplate")
	public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
		final int database = jedisConnectionFactory.getDatabase();
		return new StringRedisTemplate(jedisConnectionFactory) {
			@Override
			protected RedisConnection createRedisConnectionProxy(RedisConnection connection) {
				return super.createRedisConnectionProxy(connection);
			}

			@Override
			protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
				Integer index = RedisDbIndexThreadLocal.getIndex();
				if(index != null && index >=0 && index <= 15 && database != index) {
					connection.select(index);
				}
				return super.preProcessConnection(connection, existingConnection);
			}
		};
	}*/
	
	@SuppressWarnings("unchecked")
	@Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redisson() throws IOException {
        Config config;
        Method clusterMethod = ReflectionUtils.findMethod(RedisProperties.class, "getCluster");
        Method usernameMethod = ReflectionUtils.findMethod(RedisProperties.class, "getUsername");
        Method timeoutMethod = ReflectionUtils.findMethod(RedisProperties.class, "getTimeout");
        Object timeoutValue = ReflectionUtils.invokeMethod(timeoutMethod, redisProperties);
        int timeout;
        if(null == timeoutValue){
            timeout = 10000;
        }else if (!(timeoutValue instanceof Integer)) {
            Method millisMethod = ReflectionUtils.findMethod(timeoutValue.getClass(), "toMillis");
            timeout = ((Long) ReflectionUtils.invokeMethod(millisMethod, timeoutValue)).intValue();
        } else {
            timeout = (Integer)timeoutValue;
        }

        String username = null;
        if (usernameMethod != null) {
            username = (String) ReflectionUtils.invokeMethod(usernameMethod, redisProperties);
        }
        if (redisProperties.getSentinel() != null) {
            Method nodesMethod = ReflectionUtils.findMethod(Sentinel.class, "getNodes");
            Object nodesValue = ReflectionUtils.invokeMethod(nodesMethod, redisProperties.getSentinel());

            String[] nodes;
            if (nodesValue instanceof String) {
                nodes = convert(Arrays.asList(((String)nodesValue).split(",")));
            } else {
                nodes = convert((List<String>)nodesValue);
            }

            config = new Config();
            config.useSentinelServers()
                .setMasterName(redisProperties.getSentinel().getMaster())
                .addSentinelAddress(nodes)
                .setDatabase(redisProperties.getDatabase())
                .setConnectTimeout(timeout)
                .setUsername(username)
                .setPassword(redisProperties.getPassword());
        } else if (clusterMethod != null && ReflectionUtils.invokeMethod(clusterMethod, redisProperties) != null) {
            Object clusterObject = ReflectionUtils.invokeMethod(clusterMethod, redisProperties);
            Method nodesMethod = ReflectionUtils.findMethod(clusterObject.getClass(), "getNodes");
            List<String> nodesObject = (List<String>) ReflectionUtils.invokeMethod(nodesMethod, clusterObject);

            String[] nodes = convert(nodesObject);

            config = new Config();
            config.useClusterServers()
                .addNodeAddress(nodes)
                .setConnectTimeout(timeout)
                .setUsername(username)
                .setPassword(redisProperties.getPassword());
        } else {
            config = new Config();
            String prefix = REDIS_PROTOCOL_PREFIX;
            Method method = ReflectionUtils.findMethod(RedisProperties.class, "isSsl");
            if (method != null && (Boolean)ReflectionUtils.invokeMethod(method, redisProperties)) {
                prefix = REDISS_PROTOCOL_PREFIX;
            }
            config.useSingleServer()
                .setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setConnectTimeout(timeout)
                .setDatabase(redisProperties.getDatabase())
                .setUsername(username)
                .setPassword(redisProperties.getPassword());
        }
        return Redisson.create(config);
    }
	
	private String[] convert(List<String> nodesObject) {
        List<String> nodes = new ArrayList<String>(nodesObject.size());
        for (String node : nodesObject) {
            if (!node.startsWith(REDIS_PROTOCOL_PREFIX) && !node.startsWith(REDISS_PROTOCOL_PREFIX)) {
                nodes.add(REDIS_PROTOCOL_PREFIX + node);
            } else {
                nodes.add(node);
            }
        }
        return nodes.toArray(new String[nodes.size()]);
    }
	
	@Bean
	public RedisDbIndexSwitchProcessor redisDbIndexSwitchProcessor() {
		return new RedisDbIndexSwitchProcessor();
	}

    /**
     * redis 锁工具
     * @param redissonClient
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RedisLockHelper redisLockHelper(RedissonClient redissonClient) {
        return new RedisLockHelper(redissonClient);
    }

    /**
     * JedisClient
     * redis缓存jedis客户端
     * 需要切换库时配置DbIndex注解一起配置使用
     * 常规缓存操作请使用RedisTemplate
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public JedisClient jedisClient() {
        if(this.redisProperties.getCluster() != null) {
            Set<HostAndPort> set = this.redisProperties.getCluster().getNodes().stream().map(HostAndPort::from).collect(Collectors.toSet());
            JedisCluster jedisCluster = new JedisCluster(set);
            JedisClient jedisClient = new JedisClientCluster(jedisCluster);
            return jedisClient;
        }else {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            JedisPool jedisPool = new JedisPool(jedisPoolConfig, this.redisProperties.getHost(), this.redisProperties.getPort(), 5000, this.redisProperties.getPassword(), this.redisProperties.getDatabase());
            JedisClient jedisClient = new JedisClientPool();
            return (JedisClient)Proxy.newProxyInstance(jedisClient.getClass().getClassLoader(), jedisClient.getClass().getInterfaces(), (proxy, method, args) -> {
                Jedis jedis = jedisPool.getResource();
                jedis.select(RedisDbIndexThreadLocal.getIndex());
                JedisPoolThreadLocal.setJedis(jedis);
                try{
                    return method.invoke(jedisClient, args);
                }finally {
                    jedis.close();
                    JedisPoolThreadLocal.remove();
                    RedisDbIndexThreadLocal.remove();
                }
            });
        }

    }
	
}
