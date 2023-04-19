package com.bovine.taotao.admin.web.model;

import com.bovine.taotao.common.core.BaseModel;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统用户条件参数映射
 * @author eden
 * @date 2022/10/23 21:21:21
 */
@Getter
@Setter
public class UserModel extends BaseModel {
	
	private static final long serialVersionUID = 1L;

	public UserModel() {
		super();
	}

	public UserModel(int pageNum, int pageSize) {
		super.pageNum = pageNum;
		super.pageSize = pageSize;
	}

	private String username;
}
