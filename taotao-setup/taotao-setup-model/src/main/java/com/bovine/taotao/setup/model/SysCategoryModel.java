package com.bovine.taotao.setup.model;

import java.io.Serial;

import com.bovine.taotao.common.core.BaseModel;

import lombok.Getter;
import lombok.Setter;

/**
 * 后台管理系统 查询类目table列表数据的查询条件
 * smart-admin-web -> SysArticleCategoryController -> list()
 * @author eden
 * @date 2023年2月22日 下午10:39:36
 */
@Getter
@Setter
public class SysCategoryModel extends BaseModel {

	@Serial
	private static final long serialVersionUID = 1L;
	
	/**
	 * 类目名称
	 */
	private String name;

}
