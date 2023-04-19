package com.bovine.taotao.common.mybatis.entity;

import java.io.Serializable;

/**
 * 更新者实体类
 * @author eden
 * @date 2023年2月19日 下午4:18:26
 * @param <T>
 */
public class UpdateEntity<T extends Serializable> extends BaseEntity<T> {

	/**
	 * 更新者(ID)
	 */
	protected Long updater;
	
    /**
     * 更新人名称
     */
    protected String updateName;

	public Long getUpdater() {
		return updater;
	}

	public void setUpdater(Long updater) {
		this.updater = updater;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
    
}
