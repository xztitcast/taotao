package com.bovine.taotao.common.core.utils;

import java.io.Serializable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cglib.beans.BeanCopier;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * cglib属性拷贝工具
 * @author eden
 * @date 2023年2月21日 下午3:10:57
 */
@Slf4j
public class BeanCopierUtil {
	
	private static final long TIME_OUT = 3600000L;

	private static final Map<String, CacheEntity> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();
	
	/**
	 * 定时任务1分钟清理一次
	 * 
	 */
	static {
		Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                refresh();
            }
        }, 0, 600000);
	}
	
	/**
	 * 属性拷贝
	 * @param source
	 * @param target
	 */
	public static void copyProperties(Object source, Object target) {
		String key = source.getClass().getName() + target.getClass().getName();
		CacheEntity cacheEntity;
		if(BEAN_COPIER_CACHE.containsKey(key)) {
			cacheEntity = BEAN_COPIER_CACHE.get(key);
			cacheEntity.setCreateTime(System.currentTimeMillis());
			cacheEntity.setCacheTime(TIME_OUT);
		}else {
			BeanCopier beanCopier = BeanCopier.create(source.getClass(), target.getClass(), true);
			cacheEntity = new CacheEntity(beanCopier, TIME_OUT);
			BEAN_COPIER_CACHE.put(key, cacheEntity);
		}
		cacheEntity.getValue().copy(source, target, (v, t, c) -> {
			if(v instanceof Serializable) {
				return (Serializable) v;
			}
			return v;
		});
	}
	
	@Getter
	@Setter
	private static class CacheEntity {
		 /**
	     * 要存储的数据
	     */
	    private BeanCopier value;

	    /**
	     * 创建的时间 单位ms
	     */
	    private long createTime = System.currentTimeMillis();

	    /**
	     * 缓存的有效时间 单位ms （小于等于0表示永久保存）
	     */
	    private long cacheTime;
	    
	    private CacheEntity(BeanCopier value, long cacheTime) {
	    	this.value = value;
	    	this.cacheTime = cacheTime;
	    }
	}
	
	 /**
     * 缓存刷新,清除过期数据
     */
    public static void refresh(){
        for (String key : BEAN_COPIER_CACHE.keySet()) {
            if(isExpire(key)){
            	BEAN_COPIER_CACHE.remove(key);
            }
        }
        log.info("[BeanCopierUtil拷贝工具缓存对象个数] : {}", BEAN_COPIER_CACHE.size());
    }
	
	/**
     * 判断当前数据是否已过期
     * @param key
     * @return
     */
    private static boolean isExpire(String key){
        if(key.isEmpty()){
            return false;
        }
        if(BEAN_COPIER_CACHE.containsKey(key)){
            CacheEntity cacheEntity = BEAN_COPIER_CACHE.get(key);
            long createTime = cacheEntity.getCreateTime();
            long currentTime = System.currentTimeMillis();
            long cacheTime = cacheEntity.getCacheTime();
            if(cacheTime > 0 && currentTime - createTime > cacheTime){
                return true;
            }
            return false;
        }
        return false;
    }

}
