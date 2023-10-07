package com.bovine.taotao.common.mybatis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class TissueEntity extends BaseEntity {
	
	/**
	 * 机构ID
	 */
	protected Long tisid;

	/**
	 * 机构名称
	 */
	protected String tisname;
	
	@JsonIgnore
	public Long getTisid() {
		return tisid;
	}

	public void setTisid(Long tisid) {
		this.tisid = tisid;
	}

	@JsonIgnore
	public String getTisname() {
		return tisname;
	}

	public void setTisname(String tisname) {
		this.tisname = tisname;
	}
	
}
