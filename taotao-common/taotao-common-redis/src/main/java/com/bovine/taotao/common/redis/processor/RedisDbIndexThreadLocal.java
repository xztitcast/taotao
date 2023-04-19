package com.bovine.taotao.common.redis.processor;

/**
 * Redis数据库索引
 * @author eden
 * @date 2023年3月5日 下午12:00:20
 */
public abstract class RedisDbIndexThreadLocal {

	private static final ThreadLocal<Integer> DB_INDEX = new ThreadLocal<>();
	
	public static void doSwitch(int db){
		DB_INDEX.set(db);
    }

    public static Integer getIndex(){
        return DB_INDEX.get();
    }
    
    public static void remove() {
    	DB_INDEX.remove();
    }

}
