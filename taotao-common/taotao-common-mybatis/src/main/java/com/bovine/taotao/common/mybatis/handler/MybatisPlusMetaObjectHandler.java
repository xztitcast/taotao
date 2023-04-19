package com.bovine.taotao.common.mybatis.handler;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
	
	private static final String CREATED = "created";
	
	private static final String UPDATED = "updated";

	@Override
	public void insertFill(MetaObject metaObject) {
		//this.strictInsertFill(metaObject, CREATED, () -> LocalDateTime.now(), LocalDateTime.class);
		//this.strictInsertFill(metaObject, UPDATED, () -> LocalDateTime.now(), LocalDateTime.class);
		Date date = new Date();//向下兼容使用java.util.Date
		this.strictInsertFill(metaObject, CREATED, Date.class, date);
		this.strictInsertFill(metaObject, UPDATED, Date.class, date);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		//this.strictUpdateFill(metaObject, UPDATED, () -> LocalDateTime.now(), LocalDateTime.class);
		this.strictUpdateFill(metaObject, UPDATED, Date.class, new Date());
	}

}
