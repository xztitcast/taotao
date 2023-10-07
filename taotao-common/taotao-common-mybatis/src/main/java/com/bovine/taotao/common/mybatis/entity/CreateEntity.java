package com.bovine.taotao.common.mybatis.entity;

/**
 * 创建者实体类
 * @author eden
 * @date 2023年2月19日 下午4:17:49
 */
public abstract class CreateEntity extends TissueEntity {

	/**
	 * 创建者(ID)
	 */
	protected Long creator;
	
    /**
    * 创建人名称
    */
    protected String createName;

	/**
	 * 更新者(ID)
	 */
	protected Long updater;

	/**
	 * 更新人名称
	 */
	protected String updateName;

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
