package com.bovine.taotao.common.mybatis.entity;

import java.io.Serializable;

/**
 * 创建者实体类
 * @author eden
 * @date 2023年2月19日 下午4:17:49
 * @param <T>
 */
public class CreateEntity<T extends Serializable> extends UpdateEntity<T> {

	/**
	 * 创建者(ID)
	 */
	protected Long creator;
	
    /**
    * 创建人名称
    */
    protected String createName;

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

}
