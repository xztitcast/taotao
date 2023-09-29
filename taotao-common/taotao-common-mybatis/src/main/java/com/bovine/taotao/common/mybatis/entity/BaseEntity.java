package com.bovine.taotao.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

/**
 * 基础实体类,所有数据库对象必须集成该基础类
 * 主键ID防止遍历直接进行加密序列化与反序列化
 * @author eden
 * @date 2023年2月14日 下午11:19:11
 */
public abstract class BaseEntity {
	
	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	protected Date created;
	
	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	protected Date updated;
	
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
